package com.zhang.cloud.aop;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author 98549
 * @date 2021/10/28 1:10
 */
@Aspect
@Component
public class AuthenticationImpl {

    @Pointcut("@annotation(com.zhang.cloud.aop.Authentication)")
    private void pointCut(){ }

    @After(value="pointCut() && @annotation(authentication)")
    public void after(Authentication authentication){
        System.out.println("method start time:" + System.currentTimeMillis());
    }
}
