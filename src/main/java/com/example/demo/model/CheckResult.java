package com.example.demo.model;

import io.jsonwebtoken.Claims;
import lombok.Data;

/**
 * @author ligegang
 * @title: CheckResult
 * @projectName springBootDemo
 * @description: TODO
 * @date 2020/1/19 16:40
 */
@Data
public class CheckResult {
    private String errCode;
    private Claims claims;
    private Boolean success;



}
