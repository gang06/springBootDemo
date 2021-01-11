package com.example.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

@Data
@ConfigurationProperties(prefix = "spring.redisson")
public class RedissonProperties {

    private static final String defaultAddress = "127.0.0.1:2181";

    String address;
    String password;

    /**
     * 最大连接数
     */
    Integer maxTotal = 8;
    /**
     * 最大空闲连接数
     */
    Integer maxIdle = 1;
    /**
     * 获取连接时的最大等待毫秒数,小于零:阻塞不确定的时间
     */
    Long maxWaitMillis = 1000L;
    /**
     * database index
     */
    Integer database = 0;
    /**
     * 在获取连接的时候检查有效性
     */
    Boolean testOnBorrow = true;


    public String[] getRedisAddress() {
        if (StringUtils.isEmpty(address)) {
            return new String[]{defaultAddress};
        } else {
            return address.split(",");
        }
    }


}