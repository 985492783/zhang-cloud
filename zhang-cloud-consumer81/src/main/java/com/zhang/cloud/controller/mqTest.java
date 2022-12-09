package com.zhang.cloud.controller;

import com.zhang.cloud.entities.CommonResult;
import com.zhang.cloud.entities.Order;
import com.zhang.cloud.service.RocketmqService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 98549
 * @date 2022/1/4 22:11
 */
@RestController
public class mqTest {
    @Resource
    private RocketmqService rocketmqService;
    @PostMapping("/api/consumer/mq")
    public CommonResult send(@RequestBody Order order) {
        return rocketmqService.sendAsync(order);
    }
    @GetMapping("/mq/test2")
    public CommonResult sendSync(@RequestParam("msg") String msg) {
        return rocketmqService.sendSync(msg);
    }
}
