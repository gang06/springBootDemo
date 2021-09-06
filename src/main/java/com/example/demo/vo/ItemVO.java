package com.example.demo.vo;

import lombok.Data;

/**
 * @Description:
 * @author gang
 * @date 2021/9/6 16:37
 * @version 1.0
 */
@Data
public class ItemVO extends BaseVO{

    private String title;
    private String pubTime;
    private String collectTime;
    private String rawUrl;
    private String comeFrom;
    private String sourceUrl;
    private String content;
    private String taskId;
    private String offset;
    private String videoUrl;
    private String pic;
    private ExtendVO extend;

}
