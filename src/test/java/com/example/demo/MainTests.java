package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.SayHelloService;
import com.example.demo.utils.AESUtil;
import com.example.demo.utils.RSAEncryptUtil;
import com.example.demo.utils.RSASignUtil;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.SecretKeySpec;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
public class MainTests {

    public static void main(String[] args) throws Exception {
        List<? super Number> superList = new ArrayList<>();
        List<? extends Number> extendList = new ArrayList<>();
        superList.add(new Integer(23));
        extendList.get(0);


    }

}
