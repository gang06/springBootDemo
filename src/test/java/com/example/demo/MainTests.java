package com.example.demo;

import com.example.demo.utils.SignUtil;
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
    private static String privateKe2 = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIQp9P+oM2ugBtQJkZAvfZQs6czK39SUVxnJbpSsseHdwjn/ETp4rF4TNzAP4SvBN+sN5k+q7fzDfXmk6Lx+n5nh2VZx3qgsFv8jc1eCiFn44lTKNZ9poBohRlatpte8FTE0irCSMndbFd4guiMy428xpc9BaeqiDs/0QX2Ma8RdAgMBAAECgYBFglucajZBEHUG59Fq589AMy7zym9WOn5kzZAfGYSkqiEQp5nECtt8ztZjrLZccM6cDT9n/xoTqi0/4P+Ww3HkloxiJhojjydRIhvXEDdq5jv8CIzO3CEDwpXVoJdaxt2jyJ0LLk8wms/ddYZ3d23j4kFRGcer9GV0sbbEbOuNgQJBAMpHbVOvln4R4qOzjkwdjPunPceUPDRsFAmc65puh98OLkUKqnM4Znaib614WqgVQMPO3Gq6V/SIPHxB5NbDG1kCQQCnQ4iqOoY3qzXrSZiUOAj9D5RY7ZCnuzMD1ZBXbIPOtGbMrOK2iHfJ261khaF2DwLJL13Tj4i5k7L+8H0VM8SlAkAEMDhuTCt0jtA/jX87yjzaFRttX39jon1DubETMIi1sKh6m37uBdPxTum7EMvnwRDnPQhJWXRqxsth00d2vvF5AkEAkUI6JrPKqf/bRKnWgyoizDrCd3RhAvm09Sh8QSAxW8sZ17hw71qWxUwtSjYvIYspvTSQhFGLyLZ3ZaXOyyH5LQJAIzpIq+SqReZ3pU9tvdRdE+ipzyIT5HVDggy8icjPWtOkSP0bRIRjd1xLD+6qpTHiumtrRRucfXmBdN7dM+BU8A==";
    //RSA签名的公钥
    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDFAxshnNelWi3q1f5pXLtnt+yWxZVIGMUZAfrJEJu8HRYpr4VGKNQ/5eQTy7FJkZb+OtFG/Uy2Cgv7RiML3rqHXCXnw7ErbspTOs4HHzU6cl1ps+DI47InhZr25rq51bZdqJdSn7QRVELQ/zoXk2WRz6JDMcyzXy3BEssANnOIXQIDAQAB";

    public static void main(String[] args) throws Exception {
        System.out.println(privateKe2.length());
        // 生成签名
        StringBuilder param = new StringBuilder();
        Map<String, Object> signTreeMap = new TreeMap<>();
        signTreeMap.put("name", "张三");
        signTreeMap.put("city", "长沙");
        Set<Map.Entry<String, Object>> entrySet = signTreeMap.entrySet();
        entrySet.forEach(entry -> param.append(entry.getKey()).append("=").append(entry.getValue()).append("&"));
        String finalParam = param.substring(0, param.length() - 1);
        String sign = SignUtil.createSignature(privateKey, finalParam);
        log.info("数字签名sign={}", sign);
        boolean verifySignatureResult = SignUtil.verifySignatureByStr(publicKey, sign, finalParam);
        log.info("验签:{}", verifySignatureResult ? "成功" : "失败");
    }


}
