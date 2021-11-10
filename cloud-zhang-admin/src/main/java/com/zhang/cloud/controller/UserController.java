package com.zhang.cloud.controller;

import com.zhang.cloud.entities.CommonResult;
import com.zhang.cloud.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 98549
 * @date 2021/11/9 18:54
 */
@RestController
@RequestMapping("api/admin")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/getUser")
    public CommonResult getAllUser(){
        return userService.findAllUser();
    }
    @GetMapping("/user/{userId}")
    public CommonResult getUserById(@PathVariable("userId") Long id){
        return userService.findUserById(id);
    }
    @GetMapping("/updateUrl/{userId}")
    public CommonResult updateUrl(@PathVariable("userId") Long id,
                                  @Param("url") String url){
        return userService.updateUrlById(id,url);
    }
}
