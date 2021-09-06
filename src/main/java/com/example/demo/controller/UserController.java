package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.event.EventPublish;
import com.example.demo.model.RegisterAuthDO;
import com.example.demo.model.TestModel;
import com.example.demo.service.RegisterAuthService;
import com.example.demo.service.SayHelloService;
import com.example.demo.utils.AESUtil;
import com.example.demo.utils.MessageSourceHandler;
import com.example.demo.utils.RSAEncryptUtil;
import com.example.demo.utils.RSASignUtil;
import com.example.demo.vo.MembershipInfoVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

/**
 * @author ligegang
 * @title: UserController
 * @projectName springBootDemo
 * @description: TODO
 * @date 2019/11/13 15:36
 */
@RestController
@ResponseBody
@RequestMapping(value = "/user/")
@Slf4j
@Api(value = "用户模块")
public class UserController {

    //RSA签名的私钥
    private static String SIGN_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMUDGyGc16VaLerV/mlcu2e37JbFlUgYxRkB+skQm7wdFimvhUYo1D/l5BPLsUmRlv460Ub9TLYKC/tGIwveuodcJefDsStuylM6zgcfNTpyXWmz4MjjsieFmvbmurnVtl2ol1KftBFUQtD/OheTZZHPokMxzLNfLcESywA2c4hdAgMBAAECgYBLAXa34lW7j0zCBnzYskRrJXv+nvTJwRxw++7108vm35ALiNaNsoe0WBrwanFx1+sLmWsJwvAMsmCDZt167G5I5YjClAYfnDe3t/OUaVkUTGO32TCB4twx4BDnH/vSfy+EzdTu3CASvAdgh4HeCDRFTIvpMwQPJqo4m/WM4szKwQJBAOODyB2VW04+SugMEdS7erqwBGTqnoqShzmBP0eUUu3Hks9ECBaf4YbCtyd+4b4JdqSWYJGE6LsRld4cDvp/VHECQQDdrapD4aRVX3k3+cead/eeuX1uUBWRUTyLucXlCDt5kIsJpzY1p5Mb+pSV6JsRt4dfhlT/DOg2qWA3ymEjOvitAkEA1YclFb7Lhs+n9cj+Iy4hrXztYtlgGqYTC8Fl5oQqoMeh3az3+mPrglLVGthWfcjb9PS9hVW8J3YFtgOXezptwQJAHo6z68uM5Z/Vi9vIoghrf9u96JjtgCyclf4zw1CRMj60i84a8OZ6pt6x4MBBr/2GkapoyQe0cuSCOO6S2VJluQJBANFP76otWDX0Y9MgVzWHQqQlQoGPKDbWOu+v2W3jBaj88MubNdGACz+IRupU1Kw0qMSJnjmu4TZUcL3RzNY00vo=";
    //RSA签名的公钥
    private static String SIGN_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDFAxshnNelWi3q1f5pXLtnt+yWxZVIGMUZAfrJEJu8HRYpr4VGKNQ/5eQTy7FJkZb+OtFG/Uy2Cgv7RiML3rqHXCXnw7ErbspTOs4HHzU6cl1ps+DI47InhZr25rq51bZdqJdSn7QRVELQ/zoXk2WRz6JDMcyzXy3BEssANnOIXQIDAQAB";

    //RSA加密的公钥
    private static String ENCRYPT_PUBLIC_KEY ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC7oHvvP3sjpTB61K5q6Uw/5mHi74NhnXbzJvpaCuzMEGnCd+BbT1NlnfLmEPf61ppf+9v5cmW2AAJ00y46yTkrcMD9s+ZOX153p+mfLIrI9nw0COnGF5F7Fc7+LU7SGtO03s7iLWeZspcCa+yEYVfd/df5lOtnTIhMVsFqlgHPRwIDAQAB";

    //RSA解密的私钥
    private static String ENCRYPT_PRIVATE_KEY ="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALuge+8/eyOlMHrUrmrpTD/mYeLvg2GddvMm+loK7MwQacJ34FtPU2Wd8uYQ9/rWml/72/lyZbYAAnTTLjrJOStwwP2z5k5fXnen6Z8sisj2fDQI6cYXkXsVzv4tTtIa07TezuItZ5mylwJr7IRhV9391/mU62dMiExWwWqWAc9HAgMBAAECgYBy6tx9sNfvASIsWU9q0/GLwcN6h1Bk3VkZnGoB9GcMpZDhNyMHShK+TnuTRhlcXUy4NT6KDfMWAfN/MklCYDTOxL0lLuJKHjF4L0/YeT95MCrGb8HEXL/iNOSEJYcM7903i7RScRGcVO91vQqgcEpPOo7emsep3gavbSH4ul6vIQJBAPHov5qhLxJHy6/Bf7VvTQ6ZiYts6u6NPSsDmH23iRkkZ189sMHI7qjaN6sg4awsWjhzscycySQluiv2myb+4U0CQQDGjkzeha75cQqBz/WR18nea9IKiP+ZVrL7GqB5nYuLogp5zXYPk6oAWdGjRvhuO5BksVdBxk9YKb/VHkuD/CjjAkEAxEk/1z88S4/4BVdzwYtbMiKBCaHKCuCNpm5XeILI+p6gRhZvav4YMOhHzVJz6tleJ3ow5b6+Mtdt9ag3AnBD7QJAcrWnRy9oZQefkFt6feVy5KLZ1+hJ8maaRGOfrmMMSYE2GCRRPrKKlmVNrczEnbqfsuyZgpB1KnQENbWo3vmBDQJAVCq7GrYh5G8GeOfwHNf/Sgf21rFvyJ4whMcssDB01bF7E8zpBGENtFr2jTBjre6edJ16rHYYZpZW5s4JEZ6vaw==";

    private static final String KEY_ALGORITHM = "AES";

    @Resource
    private SayHelloService sayHelloService;

    @Resource
    private MessageSourceHandler messageSourceHandler;

    @Resource
    private RegisterAuthService registerAuthService;

    @Resource
    private EventPublish eventPublish;

    @RequestMapping(value = "hello", method = RequestMethod.POST)
    @ApiOperation(value = "打招呼")
    public String sayHello(@RequestBody TestModel testModel) throws Exception {
        // 生成签名
        StringBuilder param = new StringBuilder();
        Map<String, Object> signParamMap = new TreeMap<>();
        String jsonParam = JSON.toJSONString(testModel.getData());
        signParamMap.put("data", jsonParam);
        signParamMap.put("version", "1.0");
        signParamMap.put("timestamp", 1630658637577L);//时间戳用来做防回放攻击,与当前时间对比,限制某个时间内的数据有效
        Set<Map.Entry<String, Object>> entrySet = signParamMap.entrySet();
        entrySet.forEach(entry -> param.append(entry.getKey()).append("=").append(entry.getValue()).append("&"));
        String finalParam = param.substring(0, param.length() - 1);
        log.info(finalParam);
        String sign = RSASignUtil.createSignature(SIGN_PRIVATE_KEY, finalParam);
        log.info("数字签名sign={}", sign);
        boolean verifySignatureResult = RSASignUtil.verifySignatureByStr(SIGN_PUBLIC_KEY, sign, finalParam);
        log.info("验签:{}", verifySignatureResult ? "成功" : "失败");

        //加解密
        SecretKeySpec secretKey = AESUtil.getSecretKey();
        String aesSecretKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        String encrypt = AESUtil.encrypt(jsonParam,secretKey);
        String decrypt = AESUtil.decrypt(encrypt,secretKey);
        log.info("加解密结果:{}", jsonParam.equals(decrypt) ? "成功" : "失败");
        byte[] secretKeyArray = Base64.getDecoder().decode(aesSecretKey);
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyArray, KEY_ALGORITHM);

        String decrypt2 = AESUtil.decrypt(encrypt,secretKeySpec);
        log.info("加解密结果:{}", jsonParam.equals(decrypt2) ? "成功" : "失败");
        return "test";
    }

    @RequestMapping(value = "buy_member", method = RequestMethod.POST)
    public MembershipInfoVO buyMember(String name) {
        MembershipInfoVO membershipInfoVO = new MembershipInfoVO();
        return membershipInfoVO;
    }

    @RequestMapping(value = "register/records", method = RequestMethod.GET)
    public PageInfo<RegisterAuthDO> listRegisterRecords(String openId) {
        PageHelper.startPage(2, 2);
        RegisterAuthDO queryParam = new RegisterAuthDO();
        queryParam.setOpenId(openId);
        List<RegisterAuthDO> result = registerAuthService.list(queryParam);
        PageInfo<RegisterAuthDO> pageInfo = new PageInfo<>(result);
        log.info("pageInfo={}", pageInfo);

        return pageInfo;
    }

    @RequestMapping(value = "/upload_head_url", method = RequestMethod.POST)
    public void uploadHeadUrl(@RequestParam MultipartFile multipartFile) {
        System.out.println(multipartFile.toString());
    }


}
