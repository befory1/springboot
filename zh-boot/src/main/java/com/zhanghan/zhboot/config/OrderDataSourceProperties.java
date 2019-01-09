package com.zhanghan.zhboot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@Data
@ConfigurationProperties(prefix = "order.datasource")
public class OrderDataSourceProperties {

    private String url;

    private String username;

    private String password;

    private Integer connectionTimeout;

    private Integer idleTimeout;

    private Integer maxLifetime;

    private Integer maximumPoolSize;

    private Integer minimumIdle;
}
