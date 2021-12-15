package com.zhang.cloud.controller;

import com.zhang.cloud.entities.CommonResult;
import com.zhang.cloud.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 98549
 * @date 2021/11/28 20:07
 */
@RestController
public class TestController {

    @Resource
    public TestService testService;

    @GetMapping("/A")
    public CommonResult A(){
        return testService.A();
    }

    @GetMapping("/B")
    public CommonResult B(){
        return testService.B();
    }
}
