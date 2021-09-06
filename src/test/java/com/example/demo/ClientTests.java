package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.utils.AESUtil;
import com.example.demo.utils.RSAEncryptUtil;
import com.example.demo.utils.RSASignUtil;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Slf4j
public class ClientTests {
    //RSA签名的私钥
    private static String SIGN_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMUDGyGc16VaLerV/mlcu2e37JbFlUgYxRkB+skQm7wdFimvhUYo1D/l5BPLsUmRlv460Ub9TLYKC/tGIwveuodcJefDsStuylM6zgcfNTpyXWmz4MjjsieFmvbmurnVtl2ol1KftBFUQtD/OheTZZHPokMxzLNfLcESywA2c4hdAgMBAAECgYBLAXa34lW7j0zCBnzYskRrJXv+nvTJwRxw++7108vm35ALiNaNsoe0WBrwanFx1+sLmWsJwvAMsmCDZt167G5I5YjClAYfnDe3t/OUaVkUTGO32TCB4twx4BDnH/vSfy+EzdTu3CASvAdgh4HeCDRFTIvpMwQPJqo4m/WM4szKwQJBAOODyB2VW04+SugMEdS7erqwBGTqnoqShzmBP0eUUu3Hks9ECBaf4YbCtyd+4b4JdqSWYJGE6LsRld4cDvp/VHECQQDdrapD4aRVX3k3+cead/eeuX1uUBWRUTyLucXlCDt5kIsJpzY1p5Mb+pSV6JsRt4dfhlT/DOg2qWA3ymEjOvitAkEA1YclFb7Lhs+n9cj+Iy4hrXztYtlgGqYTC8Fl5oQqoMeh3az3+mPrglLVGthWfcjb9PS9hVW8J3YFtgOXezptwQJAHo6z68uM5Z/Vi9vIoghrf9u96JjtgCyclf4zw1CRMj60i84a8OZ6pt6x4MBBr/2GkapoyQe0cuSCOO6S2VJluQJBANFP76otWDX0Y9MgVzWHQqQlQoGPKDbWOu+v2W3jBaj88MubNdGACz+IRupU1Kw0qMSJnjmu4TZUcL3RzNY00vo=";
    //RSA签名的公钥
    private static String SIGN_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDFAxshnNelWi3q1f5pXLtnt+yWxZVIGMUZAfrJEJu8HRYpr4VGKNQ/5eQTy7FJkZb+OtFG/Uy2Cgv7RiML3rqHXCXnw7ErbspTOs4HHzU6cl1ps+DI47InhZr25rq51bZdqJdSn7QRVELQ/zoXk2WRz6JDMcyzXy3BEssANnOIXQIDAQAB";

    //RSA加密的公钥
    private static String ENCRYPT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC7oHvvP3sjpTB61K5q6Uw/5mHi74NhnXbzJvpaCuzMEGnCd+BbT1NlnfLmEPf61ppf+9v5cmW2AAJ00y46yTkrcMD9s+ZOX153p+mfLIrI9nw0COnGF5F7Fc7+LU7SGtO03s7iLWeZspcCa+yEYVfd/df5lOtnTIhMVsFqlgHPRwIDAQAB";

    //RSA解密的私钥
    private static String DECRYPT_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALuge+8/eyOlMHrUrmrpTD/mYeLvg2GddvMm+loK7MwQacJ34FtPU2Wd8uYQ9/rWml/72/lyZbYAAnTTLjrJOStwwP2z5k5fXnen6Z8sisj2fDQI6cYXkXsVzv4tTtIa07TezuItZ5mylwJr7IRhV9391/mU62dMiExWwWqWAc9HAgMBAAECgYBy6tx9sNfvASIsWU9q0/GLwcN6h1Bk3VkZnGoB9GcMpZDhNyMHShK+TnuTRhlcXUy4NT6KDfMWAfN/MklCYDTOxL0lLuJKHjF4L0/YeT95MCrGb8HEXL/iNOSEJYcM7903i7RScRGcVO91vQqgcEpPOo7emsep3gavbSH4ul6vIQJBAPHov5qhLxJHy6/Bf7VvTQ6ZiYts6u6NPSsDmH23iRkkZ189sMHI7qjaN6sg4awsWjhzscycySQluiv2myb+4U0CQQDGjkzeha75cQqBz/WR18nea9IKiP+ZVrL7GqB5nYuLogp5zXYPk6oAWdGjRvhuO5BksVdBxk9YKb/VHkuD/CjjAkEAxEk/1z88S4/4BVdzwYtbMiKBCaHKCuCNpm5XeILI+p6gRhZvav4YMOhHzVJz6tleJ3ow5b6+Mtdt9ag3AnBD7QJAcrWnRy9oZQefkFt6feVy5KLZ1+hJ8maaRGOfrmMMSYE2GCRRPrKKlmVNrczEnbqfsuyZgpB1KnQENbWo3vmBDQJAVCq7GrYh5G8GeOfwHNf/Sgf21rFvyJ4whMcssDB01bF7E8zpBGENtFr2jTBjre6edJ16rHYYZpZW5s4JEZ6vaw==";

    public static void main(String[] args) throws Exception {

        //组装业务参数
        JSONObject data = new JSONObject();
        data.put("name", "张三");
        data.put("city", "长沙");
        String jsonParam = data.toJSONString();
        /**
         * 将业务参数变成一个json串然后与版本号及时间戳字段按ascii排序并进行拼接进行签名
         * 1) 排序：将筛选的参数按照第一个字符的键值ASCII码递增排序（字母升序排序），如果遇到相同字符则按照第二个字符的键值ASCII码递增排序，以此类推。
         * 2) 拼接：将排序后的参数与其对应值，组合成“参数=参数值”的格式，并且把这些参数用&字符连接起来，此时生成的字符串为待签名字符串。则需将待签名字符串和私钥放入SHA2 with RSA(1024)算法中得出签名，并做Base64得到 sign的值。
         */
        Map<String, Object> signParamMap = new TreeMap<>();
        signParamMap.put("data", jsonParam);
        //接口协议1.0版本
        signParamMap.put("version", "1.0");
        //HG、LY、TW;对应系统的缩写
        signParamMap.put("appName", "LY");
        long timestamp = System.currentTimeMillis();
        signParamMap.put("timestamp", timestamp);//时间戳用来做防回放攻击,与当前时间对比,限制某个时间内的数据有效
        StringBuilder param = new StringBuilder();
        Set<Map.Entry<String, Object>> entrySet = signParamMap.entrySet();
        entrySet.forEach(entry -> param.append(entry.getKey()).append("=").append(entry.getValue()).append("&"));
        String signParam = param.substring(0, param.length() - 1);
        log.info("签名前参数signParam={}", signParam);
        String sign = RSASignUtil.createSignature(SIGN_PRIVATE_KEY, signParam);
        log.info("数字签名sign={}", sign);

        //客户端将明文数据采用AES加密,AES秘钥采用RSA进行加密
        SecretKeySpec clientSecretKey = AESUtil.getSecretKey();
        String encrypt = AESUtil.encrypt(jsonParam, clientSecretKey);
        String aesClientSecretKey = Base64.getEncoder().encodeToString(clientSecretKey.getEncoded());
        String encryptSecretKey = RSAEncryptUtil.encryptWithRSA(aesClientSecretKey, ENCRYPT_PUBLIC_KEY);

        //客户端发送的报文格式
        JSONObject request = new JSONObject();
        //数字签名
        request.put("sign", sign);
        //加密后得字符串,解密出来之后是一个json串
        request.put("data", encrypt);
        //AES加密算法的秘钥,这里已经通过RSA算法进行加密传输
        request.put("secretKey", encryptSecretKey);
        request.put("timestamp", timestamp);
        request.put("version", "1.0");
        request.put("appName", "LY");
        log.info("数据格式:{}", request.toJSONString());
    }


}
