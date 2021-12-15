package com.zhang.cloud.controller;

import com.zhang.cloud.aop.FallBack;
import com.zhang.cloud.aop.zlf4j;
import com.zhang.cloud.entities.UserDO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zhang.cloud.service.Test;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 98549
 * @date 2021/10/29 17:13
 */
@RestController
@zlf4j
public class ConsumerController{
    @Resource
    private Test test;
    public UserDO TT(){
        int a=10/0;
        return test.findUser1ById(2);
    }
    public void TestA(){
        int c=10;
    }

    @GetMapping("/test")
    @FallBack
    public UserDO test(HttpServletRequest request){
        return TT();
    }
}
