package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.example.demo.utils.RSAEncryptUtil;
import com.example.demo.utils.RSASignUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
public class MainTests {
    private static final ReadWriteLock rtLock = new ReentrantReadWriteLock();
    //RSA签名的私钥
    private static String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMUDGyGc16VaLerV/mlcu2e37JbFlUgYxRkB+skQm7wdFimvhUYo1D/l5BPLsUmRlv460Ub9TLYKC/tGIwveuodcJefDsStuylM6zgcfNTpyXWmz4MjjsieFmvbmurnVtl2ol1KftBFUQtD/OheTZZHPokMxzLNfLcESywA2c4hdAgMBAAECgYBLAXa34lW7j0zCBnzYskRrJXv+nvTJwRxw++7108vm35ALiNaNsoe0WBrwanFx1+sLmWsJwvAMsmCDZt167G5I5YjClAYfnDe3t/OUaVkUTGO32TCB4twx4BDnH/vSfy+EzdTu3CASvAdgh4HeCDRFTIvpMwQPJqo4m/WM4szKwQJBAOODyB2VW04+SugMEdS7erqwBGTqnoqShzmBP0eUUu3Hks9ECBaf4YbCtyd+4b4JdqSWYJGE6LsRld4cDvp/VHECQQDdrapD4aRVX3k3+cead/eeuX1uUBWRUTyLucXlCDt5kIsJpzY1p5Mb+pSV6JsRt4dfhlT/DOg2qWA3ymEjOvitAkEA1YclFb7Lhs+n9cj+Iy4hrXztYtlgGqYTC8Fl5oQqoMeh3az3+mPrglLVGthWfcjb9PS9hVW8J3YFtgOXezptwQJAHo6z68uM5Z/Vi9vIoghrf9u96JjtgCyclf4zw1CRMj60i84a8OZ6pt6x4MBBr/2GkapoyQe0cuSCOO6S2VJluQJBANFP76otWDX0Y9MgVzWHQqQlQoGPKDbWOu+v2W3jBaj88MubNdGACz+IRupU1Kw0qMSJnjmu4TZUcL3RzNY00vo=";
    //RSA签名的公钥
    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDFAxshnNelWi3q1f5pXLtnt+yWxZVIGMUZAfrJEJu8HRYpr4VGKNQ/5eQTy7FJkZb+OtFG/Uy2Cgv7RiML3rqHXCXnw7ErbspTOs4HHzU6cl1ps+DI47InhZr25rq51bZdqJdSn7QRVELQ/zoXk2WRz6JDMcyzXy3BEssANnOIXQIDAQAB";

    public static void main(String[] args) throws Exception {

        // 生成签名
        StringBuilder param = new StringBuilder();
        Map<String, Object> signParamMap = new TreeMap<>();
        Map<String, Object> dataMap = new TreeMap<>();
        dataMap.put("name", "张三");
        dataMap.put("city", "长沙");
        String jsonParam = JSON.toJSONString(dataMap);
        signParamMap.put("data", jsonParam);
        signParamMap.put("version", "1.0");
        signParamMap.put("timestamp", System.currentTimeMillis());//时间戳用来做防回放攻击,与当前时间对比,限制某个时间内的数据有效
        Set<Map.Entry<String, Object>> entrySet = signParamMap.entrySet();
        entrySet.forEach(entry -> param.append(entry.getKey()).append("=").append(entry.getValue()).append("&"));
        String finalParam = param.substring(0, param.length() - 1);
        String sign = RSASignUtil.createSignature(privateKey, finalParam);
        log.info("数字签名sign={}", sign);
        boolean verifySignatureResult = RSASignUtil.verifySignatureByStr(publicKey, sign, finalParam);
        log.info("验签:{}", verifySignatureResult ? "成功" : "失败");

        String encryptWithRSA = RSAEncryptUtil.encryptWithRSA(jsonParam, publicKey);
        String decryptWithRSA = RSAEncryptUtil.decryptWithRSA(encryptWithRSA, privateKey);
        log.info("解密结果:{}", jsonParam.equals(decryptWithRSA) ? "成功" : "失败");

    }


}
