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
 * File                : KeyUtil.java
 * Version             : 00.02
 * System Release Name : 00.02
 * Submit Date (M/D/Y) : 09/13/2016
 *
 ******************************************************************************
 *
 * Module Description  : This module implements the routines for RSA-1024 Key pair generation,
 * 						key pair backup/restore, pseudo random number generator.
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
 * EHUAN/		     09/18/2016   restore keys from specific path
 */

package com.example.demo.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;

public class KeyUtil {
	private KeyUtil() {
	}

	/*--	Defines the algorithm and for en/decryption and the strength	--*/
	public static final String SIGN_ALGORITHM = "RSA";
	public static final int RSA_STRENGTH = 1024;

	public static final String RSA_PRVKEY = "jd_private.key";
	public static final String RSA_PUBKEY = "jd_public.key";

	/*--	To generate a RSA keypair	--*/
	public static KeyPair keyGen() {

		KeyPairGenerator keygen = null;

		/*--	Create the key generator	--*/
		try {
			keygen = KeyPairGenerator.getInstance(SIGN_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}

		/*--	To initialize a pseudo random number generator --*/
		SecureRandom secrand = new SecureRandom();

		secrand.setSeed(getPrn());
		keygen.initialize(RSA_STRENGTH, secrand);

		/*--	To generate key pair, the performance depends on strength	--*/
		keygen.initialize(RSA_STRENGTH);

		return keygen.generateKeyPair();
	}

	/*--	To backup the key pair just generated	--*/
	public static boolean backupKey(KeyPair akey) {
		if (akey == null) {
			return false;
		}

		/*--	To save the private key and public key respectively	--*/
		try (ObjectOutputStream prvOut = new ObjectOutputStream(new FileOutputStream(RSA_PRVKEY));
			 ObjectOutputStream pubOut = new ObjectOutputStream(new FileOutputStream(RSA_PUBKEY))) {
			prvOut.writeObject(akey.getPrivate());
			pubOut.writeObject(akey.getPublic());
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public static PrivateKey restorePrvKey() throws Exception {
		return KeyUtil.getPrvKey(RSA_PRVKEY);
	}

	public static PrivateKey restorePrvKey(String privateKeyPath) throws Exception {
		return KeyUtil.getPrvKey(privateKeyPath);
	}

	/*public static PrivateKey getPrvKey(String privateKeyPath){
		*//*--	To read the private key from the specific file.	--*//*
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(privateKeyPath))) {
			return (PrivateKey) in.readObject();
		} catch (Exception e) {
			return null;
		}
	}*/

	/**
	 * 获取私钥
	 *
	 * @param privateKey 私钥字符串
	 * @return
	 */
	public static PrivateKey getPrvKey(String privateKey) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		byte[] decodedKey = Base64.decodeBase64(privateKey.getBytes());
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
		return keyFactory.generatePrivate(keySpec);
	}

	public static PublicKey restorePubKey() {
		return KeyUtil.getPubKey(RSA_PUBKEY);
	}

	public static PublicKey restorePubKey(String pubKeyPath) {
		return KeyUtil.getPubKey(pubKeyPath);
	}

	public static PublicKey getPubKey(String pubKeyPath){
		/*--	To read the public key from the specific file.	--*/
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(pubKeyPath))) {
			return (PublicKey) in.readObject();
		} catch (Exception e) {
			return null;
		}
	}

	/*--	To get a pseudo number, shall be revisited --*/
	private static byte[] getPrn() {

		Date date = new Date();
		return date.toString().getBytes();

	}
}
