package com.example.demo.service;

import com.example.demo.model.RegisterAuthDO;

import java.util.List;

/**
 * description:
 * author: ligegang
 * date: 2020/8/3 17:04
 */
public interface RegisterAuthService {
    List<RegisterAuthDO> list(RegisterAuthDO registerAuthDO);
}
