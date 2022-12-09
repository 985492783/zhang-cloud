package com.zhang.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 98549
 * @date 2021/10/29 17:09
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ConsumerMain81 {
    public static void main(String[] args) {
        System.setProperty("hhh","123456");
        SpringApplication.run(ConsumerMain81.class,args);
    }

}
