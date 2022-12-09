package com.zhang.cloud.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author 98549
 * @date 2022/1/16 18:55
 */
@Configuration
@EnableConfigurationProperties(TestConfigProperties.class)
public class RestTemplateConfig {
    @Resource
    private ApplicationContext applicationContext;
    @Bean
    public RestTemplate getTemplate(){
        return new RestTemplate();
    }

    @Bean
    public TestConfigProperties.Req getReq(){
        return new TestConfigProperties.Req();
    }
    public TestConfigProperties getConfig(){
        return applicationContext.getBean(TestConfigProperties.class);
    }
    public ApplicationContext getApplicationContext(){
        return applicationContext;
    }
}
