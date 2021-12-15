package com.zhang.cloud.controller;

import com.zhang.cloud.entities.CommonResult;
import com.zhang.cloud.entities.UserDO;
import com.zhang.cloud.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 98549
 * @date 2021/10/13 10:52
 */
@RestController
@Slf4j
public class LoginController {

    @Resource
    private UserService userService;
    @GetMapping("api/user/getUser")
    public CommonResult findByUsername(HttpServletRequest request){
        return userService.getUserByUsername(request.getHeader("token"));
    }
    @PostMapping("api/user/login")
    public CommonResult login(@RequestBody UserDO userDO){
        return userService.login(userDO);
    }

    @GetMapping("api/user/verify")
    public CommonResult verity(HttpServletRequest request){
        return userService.verify(request.getHeader("token"));
    }

    @GetMapping("api/user/logout")
    public  CommonResult logout(HttpServletRequest request){
        return userService.logout(request.getHeader("token"));
    }

    @PostMapping("api/user/register")
    public  CommonResult register(@RequestBody UserDO userDO){
        return userService.register(userDO);
    }
}
