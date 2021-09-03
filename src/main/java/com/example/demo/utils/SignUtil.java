
/**
*                       Proprietary Software Product
*
*                              WALMART ISD Solutions
*                     Copyright by WALMART, Inc. xxxx-2016
*                           All Rights Reserved
*
******************************************************************************
*
* CONTROL Information
* File                : signUtil.java
* Version             : 00.02
* System Release Name : 00.02
* Submit Date (M/D/Y) : 09/13/2016 
*
******************************************************************************
*
* Module Description  : This module implements the routines for Signature generation/verification.
*							 
*                       Flagship project.
*
*
*             Author  : XTIAN/501959
*
******************************************************************************
*
* Date   : 13SEPT2016
* Person : XTIAN/501959
* Reason : Original Development.
*/

/*
 * History:
 * Name/EmployeeNumber    Date    Description
 * ------------------- ---------- ----------------------------------------------
 * EHUAN/		     09/18/2016   verification by PublicKey string
 */

/**	
 * 					The procedures for signature generation and verification between Jingdong & Walmart
 *
 *						Methodology:
 *
 *								(JD)					system boundary						(WM)
 *				msg	 |----------------|----------|				|				 |----------------|----------|	msg					
 *						msg bdy			signature				|					msg bdy			signature
 *											^					|			SHA256 --> |				|<--- pub-key	
 *								pri-key---->|					|				SHA256(msg bdy)		decrypt(signature)
 *									SHA256(msg bdy)				|					   |-> (matched?)<--|
 *			   |--> pri-key					
 *		keygen-|		
 *			   |--> pub-key
 *
 **/

package com.example.demo.utils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.Charset;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

public class SignUtil {
	private SignUtil() {
	}

	private static final Logger log = LoggerFactory.getLogger(SignUtil.class);

	/*--	Defines the algorithm and for en/decryption and digest	--*/
	public static final String SIGN_ALGORITHM = "RSA";
	public static final String DIGEST_ALGORITHM = "SHA-256";

	public static final String CHARSET_UTF8 = "UTF-8";

	public static String createSignature(String privateKeyStr, String aData)
			throws Exception {
		PrivateKey privateKey = KeyUtil.getPrvKey(privateKeyStr);
		return createSignature((RSAPrivateKey) privateKey,aData);
	}

	/*--	TO create a signature, which is made from RSA-prikey(SHA256(INFO))	--*/
	public static String createSignature(RSAPrivateKey aPrikey, String aData)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

		if ((aPrikey == null) || (aData == null)) {
			return null;
		}

		/*--	Step #1. create the digest for the data	--*/
		byte[] bDigest = createDigest(aData);

		if (bDigest != null) {
			/*--	Step #2. encrypt the digest as the signature by the private key 	--*/
			byte[] bSign = encryptDigest(aPrikey, bDigest);
			//byte[] bSign = getSignatureSHA256(aPrikey, bDigest);
			return new String(Base64.encodeBase64(bSign));
		} else {
			return null;
		}

	}

	public static boolean verifySignatureSHA256(PublicKey publicKey, byte[] digest, byte[] signature) {

		try {
			Signature sig = Signature.getInstance("SHA256withRSA");
			sig.initVerify(publicKey);
			sig.update(digest);
			boolean ok = sig.verify(signature);
			return ok;
		} catch (Exception e) {
			log.error("验签异常", e);
			return false;
		}
	}

	/*--	TO verify a signature, the methodology is RSA-pubkey(SIGNATURE) ? SHA256(INFO)	--*/
	public static boolean verifySignature(RSAPublicKey pubkey, String aSignature, String aData) {

		boolean signResult = false;
		if ((pubkey == null) || (aSignature == null) || (aData == null)) {
			return false;
		}
		try {
			/*--	Step #1. Create the digest for the data	--*/
			byte[] bDigest = createDigest(aData);
			if (bDigest == null) {
				return signResult;
			}

			/*--	Step #2. Decrypt the signature back to the plain digest by the public key 	--*/
			byte[] bSignature = Base64.decodeBase64(aSignature);
			/*signResult = verifySignatureSHA256(pubkey,bDigest,bSignature);
			return signResult;*/
			byte[] bDigestNew = decryptSignature(pubkey, bSignature);

			if (bDigestNew == null) {
				return signResult;
			}

			//*--	Step #3. Verify if the 2 digests are matched, otherwise the validation fails.	--*//*
			signResult = Arrays.equals(bDigest, bDigestNew);
			return signResult;
		} catch (Exception e) {
			log.error("VerifySignature sign:{}, data:{}", new String[] { aSignature, aData }, e);
			return false;
		} finally {
			log.debug("VerifySignature  sign: {} ,data:{},result :{}", aSignature, aData, signResult);
		}
	}

	public static boolean verifySignature(String keyPath, String aSignature, String aData) {
		if ((keyPath == null) || keyPath.isEmpty() || (aSignature == null) || (aData == null)) {
			return false;
		}

		RSAPublicKey publicKey = (RSAPublicKey) KeyUtil.restorePubKey(keyPath);
		return verifySignature(publicKey, aSignature, aData);
	}

	public static boolean verifySignatureByStr(String publicKeyStr, String aSignature, String aData) throws NoSuchAlgorithmException, InvalidKeySpecException {
		if ((publicKeyStr == null) || publicKeyStr.isEmpty() || (aSignature == null) || (aData == null)) {
			return false;
		}
		return verifySignature(setPublicKey(new Base64().decode(publicKeyStr)), aSignature, aData);
	}

	public static String getPublicKeyStr(PublicKey publicKey) {
		return (new Base64()).encodeAsString(publicKey.getEncoded());
	}

	public static RSAPublicKey setPublicKey(byte[] publicKeyBytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return (RSAPublicKey) keyFactory.generatePublic(keySpec);
	}

	public static byte[] createDigest(String aData) throws NoSuchAlgorithmException {

		if (aData == null) {
			return null;
		}

		MessageDigest md = null;
		/*--	Create the digest by sha-256	--*/
		md = MessageDigest.getInstance(DIGEST_ALGORITHM);
		md.update(aData.getBytes(Charset.forName(CHARSET_UTF8)));
		return md.digest();

	}

	private static byte[] getSignatureSHA256(PrivateKey priKey, byte[] digest) {
		byte[] mesDigest;
		Signature sig;
		try {
			sig = Signature.getInstance("SHA256withRSA");
			sig.initSign(priKey);
			sig.update(digest);
			mesDigest = sig.sign();
			return mesDigest;
		} catch (Exception e) {
			log.error("签名计算失败", e);
			return null;
		}
	}

	private static byte[] encryptDigest(RSAPrivateKey aPrikey, byte[] aDigest)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

		if ((aPrikey == null) || (aDigest == null)) {
			return null;
		}

		/*--	Use the private key to sign the data	--*/
		Cipher cipher = null;

		/*--	use RSA to encrypt the plain text --*/
		cipher = Cipher.getInstance(SIGN_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, aPrikey);
		return cipher.doFinal(aDigest);
	}

	public static byte[] decryptSignature(RSAPublicKey aPubkey, byte[] aSignature)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

		if ((aPubkey == null) || (aSignature == null)) {
			return null;
		}

		/*--	Use the public key to verify the signature	--*/
		Cipher cipher = null;

		/*--	use RSA to decrypt the plain text --*/
		cipher = Cipher.getInstance(SIGN_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, aPubkey);
		return cipher.doFinal(aSignature);
	}
}