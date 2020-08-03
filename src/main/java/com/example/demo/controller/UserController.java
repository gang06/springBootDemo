package com.example.demo.controller;

import com.example.demo.model.RegisterAuthDO;
import com.example.demo.service.RegisterAuthService;
import com.example.demo.service.SayHelloService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

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
public class UserController {

    @Resource
    private SayHelloService sayHelloService;

    @Resource
    private RegisterAuthService registerAuthService;

    @RequestMapping("hello")
    public String sayHello(String name) {
        String result = sayHelloService.sayHello(name);
        return result;
    }

    @RequestMapping(value = "register_records", method = RequestMethod.GET)
    public List<RegisterAuthDO> listRegisterRecords(String openId) {
        RegisterAuthDO queryParam = new RegisterAuthDO();
        queryParam.setOpenId(openId);
        List<RegisterAuthDO> result = registerAuthService.list(queryParam);
        return result;
    }

    @RequestMapping(value = "/upload_head_url", method = RequestMethod.POST)
    public void uploadHeadUrl(@RequestParam MultipartFile multipartFile) {
        System.out.println(multipartFile.toString());
    }


}
