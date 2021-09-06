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

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

public abstract class KeyUtil {
    private KeyUtil() {
    }

    /*--	Defines the algorithm and for en/decryption and the strength	--*/
    public static final String RSA_ALGORITHM = "RSA";
    public static final int RSA_STRENGTH = 1024;

    /*--	To generate a RSA keypair	--*/
    public static KeyPair keyGen() {

        KeyPairGenerator keygen = null;

        /*--	Create the key generator	--*/
        try {
            keygen = KeyPairGenerator.getInstance(RSA_ALGORITHM);
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

    /**
     * 获取私钥
     *
     * @param privateKey 私钥字符串
     * @return
     */
    public static PrivateKey getPrvKey(String privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        byte[] decodedKey = Base64.decodeBase64(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 得到公钥
     * @param publicKey  密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        PublicKey key = keyFactory.generatePublic(x509KeySpec);
        return key;
    }


    /*--	To get a pseudo number, shall be revisited --*/
    private static byte[] getPrn() {

        Date date = new Date();
        return date.toString().getBytes();

    }
}
