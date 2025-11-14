package com.gary.mqtthttpbridge.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
@ConfigurationProperties(prefix = "mqtt")
@Getter
@Setter
public class MqttConfig {
    private String url;
    private String client;
    private String username;
    private String password;
    private Integer timeout;
    private String receiveTopics;
    private String sendTopics;
    private Integer keepalive;
    private Integer qos;
}
