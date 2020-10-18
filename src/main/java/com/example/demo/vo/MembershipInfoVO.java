package com.example.demo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * description:
 * author: lipanpan
 * date: 2020/5/9 下午9:53
 */
@Data
public class MembershipInfoVO extends BaseVO {

    @ApiModelProperty(value = "客户ID", example = "88881100")
    private String uid;

    @ApiModelProperty(value = "头像地址", example = "https://www.cos.dfam/images/head_url.png")
    private String headUrl;

    @ApiModelProperty(value = "会员码", example = "170808039818912")
    private String memCode;

    private List<String> list;

    private List<Set> set;

    private Integer age;

}
