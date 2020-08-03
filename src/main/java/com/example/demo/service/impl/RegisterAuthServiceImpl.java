package com.example.demo.service.impl;

import com.example.demo.mapper.RegisterAuthMapper;
import com.example.demo.model.RegisterAuthDO;
import com.example.demo.model.RegisterAuthExample;
import com.example.demo.service.RegisterAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * description:
 * author: ligegang
 * date: 2020/8/3 17:05
 */
@Service
@Slf4j
public class RegisterAuthServiceImpl implements RegisterAuthService {

    @Resource
    private RegisterAuthMapper registerAuthMapper;

    @Override
    public List<RegisterAuthDO> list(RegisterAuthDO registerAuthDO) {
        RegisterAuthExample example = new RegisterAuthExample();
        example.createCriteria()
                .andPropertyEqualTo(RegisterAuthExample.OPEN_ID,registerAuthDO.getOpenId());
        return registerAuthMapper.selectByExample(example);
    }
}
