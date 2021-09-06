package com.example.demo.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;

public abstract class RSAEncryptUtil {
    //RSA加密
    public static String encryptWithRSA(String content, String publicKey) throws Exception {

        //获取一个加密算法为RSA的加解密器对象cipher。
        Cipher cipher = Cipher.getInstance("RSA");
        //设置为加密模式,并将公钥给cipher。
        cipher.init(Cipher.ENCRYPT_MODE, KeyUtil.getPublicKey(publicKey));
        //获得密文
        byte[] secret = cipher.doFinal(content.getBytes());
        //进行Base64编码
        return new BASE64Encoder().encode(secret);
    }

    //RSA解密
    public static String decryptWithRSA(String ciphertext, String privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        //传递私钥，设置为解密模式。
        cipher.init(Cipher.DECRYPT_MODE, KeyUtil.getPrvKey(privateKey));
        //解密器解密由Base64解码后的密文,获得明文字节数组
        byte[] b = cipher.doFinal(new BASE64Decoder().decodeBuffer(ciphertext));
        //转换成字符串
        return new String(b);
    }

}
