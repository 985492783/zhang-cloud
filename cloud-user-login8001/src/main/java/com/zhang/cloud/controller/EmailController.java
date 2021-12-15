package com.zhang.cloud.controller;

import com.zhang.cloud.entities.CommonResult;
import com.zhang.cloud.service.EmailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 98549
 * @date 2021/11/17 21:02
 */
@RestController
public class EmailController {

    @Resource
    private EmailService emailService;
    @GetMapping("api/user/sendCode")
    public CommonResult sendCodeToEmail(@RequestParam("email") String email){
        return emailService.sendCode(email);
    }
}
