package com.gary.mqtthttpbridge.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  OpenAPI信息配置类
 */
@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI selfOpenAPI(){
        return new OpenAPI().info(new Info()
                        .title("数字孪生数据接口文档")
                        .description("MQTT、HTTP桥接应用接口文档")
                        .version("v1.0.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("更多文档")
                        .url("https://springdoc.org"));
                //.servers();
    }

}
