package com.example.demo.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import java.io.InputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 加密工具类
 */
public class EncryptionUtil {

    static Logger logger = LoggerFactory.getLogger(EncryptionUtil.class);

    /**
     * 签名算法
     */
    private static String signatureKey = "MD5withRSA";

    /**
     * 加密算法
     */
    private static String algorithm = "RSA";

    /**
     * 根据公钥字符串生成公钥对象
     *
     * @param publicKeyStr
     * @param algorithm    RSA  加密算法
     */
    private static PublicKey generatePublicKeyByStr(String publicKeyStr, String algorithm) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        return rsaPublicKey;
    }


    /**
     * 根据私钥字符串生成私钥对象
     *
     * @param privateKeyStr 私钥字符串
     * @param algorithm     加密算法
     * @return
     * @throws Exception
     */
    private static PrivateKey generatePrivateKeyByStr(String privateKeyStr, String algorithm) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] buffer = base64Decoder.decodeBuffer(privateKeyStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        return rsaPrivateKey;
    }


    /**
     * 根据字符串生成私钥签名
     *
     * @param string        要签名的内容
     * @param privateKeyStr 私钥字符串
     * @return
     * @throws Exception
     */
    public static String sign(String string, String privateKeyStr) throws Exception {
        PrivateKey privateKey = generatePrivateKeyByStr(privateKeyStr, algorithm);
        Signature signature = Signature.getInstance(signatureKey);
        signature.initSign(privateKey);
        signature.update(string.getBytes());
        byte[] result = signature.sign();
        return Base64.getEncoder().encodeToString(result);
    }


    /**
     * 根据签名和公钥字符串验证签名合法性
     *
     * @param sign
     * @param publicKeyStr
     * @param string       要签名的内容
     * @return
     */
    public static boolean verify(String sign, String string, String publicKeyStr) {
        try {
            PublicKey publicKey = generatePublicKeyByStr(publicKeyStr, algorithm);
            Signature signature = Signature.getInstance(signatureKey);
            signature.initVerify(publicKey);
            signature.update(string.getBytes());
            //重点 一点要解密
            boolean bool = signature.verify(Base64.getDecoder().decode(sign));
            return bool;
        } catch (Exception e) {
            logger.error("", e);
            return false;
        }
    }

    /**
     * 根据问价路径读取文件内容
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    public String getFileByFilePath(String filePath) throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(filePath);
        StringBuilder stringBuilder = new StringBuilder();
        byte[] bytes = new byte[1024];
        int count = 0;
        while ((count = in.read(bytes)) != -1) {
            stringBuilder.append(new String(bytes, 0, count));
        }
        return stringBuilder.toString();
    }


    /**
     * 生成公私钥对
     *
     * @throws Exception
     */
    public static void generatePubPrivateKey() throws Exception {
        String string = "RSA";
        KeyPairGenerator pair = KeyPairGenerator.getInstance(string);
        KeyPair pp = pair.generateKeyPair();
        //生成私钥
        PrivateKey privateKey = pp.getPrivate();
        //生成公钥
        PublicKey publicKey = pp.getPublic();
        byte[] pencode = privateKey.getEncoded();
        byte[] pubencode = publicKey.getEncoded();
        System.out.println("私钥：");
        System.out.println(com.sun.org.apache.xml.internal.security.utils.Base64.encode(pencode));
        System.out.println("公钥：");
        System.out.println(com.sun.org.apache.xml.internal.security.utils.Base64.encode(pubencode));
    }
}
