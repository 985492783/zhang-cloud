package com.zhang.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 98549
 * @date 2021/11/19 14:05
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ChatMain8002 {
    public static void main(String[] args) {
        SpringApplication.run(ChatMain8002.class,args);
    }
}
