package com.example.demo;


import com.example.demo.utils.AESUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
public class Test {

    public static final String KEY = "tencent_smmember";

    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";//默认的加密算法

    public static void main(String[] args) throws SecurityException, NoSuchMethodException {
        SecretKeySpec secretKey = AESUtil.getSecretKey();
        String aesSecretKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        String content = "故事的小黄花";
        String encrypt = AESUtil.encrypt(content,secretKey);
        log.info(encrypt);
        String decrypt = AESUtil.decrypt(encrypt,secretKey);
        log.info("加解密结果:{}", content.equals(decrypt) ? "成功" : "失败");
        byte[] secretKeyArray = Base64.getDecoder().decode(aesSecretKey);
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyArray, KEY_ALGORITHM);

        String decrypt2 = AESUtil.decrypt(encrypt,secretKeySpec);
        log.info("加解密结果:{}", content.equals(decrypt2) ? "成功" : "失败");

    }

}
