package com.example.demo.controller;

import com.example.demo.event.EventPublish;
import com.example.demo.model.RegisterAuthDO;
import com.example.demo.service.RegisterAuthService;
import com.example.demo.service.SayHelloService;
import com.example.demo.utils.MessageSourceHandler;
import com.example.demo.vo.MembershipInfoVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Api(value = "用户模块")
public class UserController {

    @Resource
    private SayHelloService sayHelloService;

    @Resource
    private MessageSourceHandler messageSourceHandler;

    @Resource
    private RegisterAuthService registerAuthService;

    @Resource
    private EventPublish eventPublish;

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    @ApiOperation(value = "打招呼")
    public String sayHello(String name) {
        System.out.println(messageSourceHandler.getMessage("RENEW_RESUME_MEMBER_BENEFITS"));
        String result = sayHelloService.sayHello(name);
        return result;
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
