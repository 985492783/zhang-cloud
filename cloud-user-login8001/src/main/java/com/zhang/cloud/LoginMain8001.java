package com.zhang.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 98549
 * @date 2021/10/13 10:37
 */
@SpringBootApplication
@EnableDiscoveryClient
public class LoginMain8001 {
    public static void main(String[] args){
        SpringApplication.run(LoginMain8001.class,args);
    }
}
