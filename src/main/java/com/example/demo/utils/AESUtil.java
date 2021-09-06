package com.example.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

/**
 * @version V1.0
 * @desc AES 加密工具类
 */
@Slf4j
public abstract class AESUtil {

    public static final String KEY_ALGORITHM = "AES";
    //默认的加密算法
    public static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    //编码方式
    public static final String CODE_TYPE = "UTF-8";

    //用于加锁
    private static final Object object = new Object();

    private static KeyGenerator kg = null;


    /**
     * AES 加密操作
     *
     * @param content  待加密内容
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(String content, SecretKeySpec secretKeySpec) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);// 创建密码器

            byte[] byteContent = content.getBytes(CODE_TYPE);

            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);// 初始化为加密模式的密码器

            byte[] result = cipher.doFinal(byteContent);// 加密

            return Base64Utils.encodeToString(result);//通过Base64转码返回
        } catch (Exception e) {
            log.error("加密失败,content={}", content, e);
        }

        return null;
    }

    /**
     * AES 解密操作
     *
     * @param content
     * @return
     */
    public static String decrypt(String content, SecretKeySpec secretKeySpec) {

        try {
            //实例化
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            //使用密钥初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            //执行操作
            byte[] result = cipher.doFinal(Base64Utils.decodeFromString(content));

            return new String(result, "utf-8");
        } catch (Exception e) {
            log.error("解密失败,content={}", content, e);
        }

        return null;
    }

    /**
     * 生成加密秘钥
     *
     * @return
     */
    public static SecretKeySpec getSecretKey() {

        try {
            if (kg == null) {
                synchronized (object) {
                    kg = KeyGenerator.getInstance(KEY_ALGORITHM);
                    //AES 密钥长度为 128
                    kg.init(128);
                }
            }
            //生成一个密钥
            SecretKey secretKey = kg.generateKey();
            return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);// 转换为AES专用密钥
        } catch (NoSuchAlgorithmException e) {
            log.error("生成AES秘钥失败,", e);
        }
        return null;
    }

}
