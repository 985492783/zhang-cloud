package com.zhang.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 98549
 * @date 2021/11/5 23:37
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AliPayMain {
    public static void main(String[] args) {
        SpringApplication.run(AliPayMain.class,args);
    }
}
