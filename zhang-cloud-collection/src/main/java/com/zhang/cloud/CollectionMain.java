package com.zhang.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * @author 98549
 * @date 2021/12/19 21:45
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CollectionMain {
    public static void main(String[] args) {
        SpringApplication.run(CollectionMain.class);
        Thread.getAllStackTraces().forEach((k,v)->{
            k.getThreadGroup();
        });
    }
}
