package com.zhang.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 98549
 * @date 2021/11/1 22:28
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GateWay9527 {
    public static void main(String[] args) {
        SpringApplication.run(GateWay9527.class,args);
    }
}
