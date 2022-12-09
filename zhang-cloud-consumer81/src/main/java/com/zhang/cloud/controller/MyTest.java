package com.zhang.cloud.controller;


import org.springframework.stereotype.Component;

/**
 * @author 98549
 * @date 2021/12/6 14:58
 */
@Component
public class MyTest {
    private int a;
    MyTest(){
        this.a=10;
    }
    public int getA() {
        return this.a;
    }
}
