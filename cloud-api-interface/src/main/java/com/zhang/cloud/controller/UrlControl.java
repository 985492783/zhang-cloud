package com.zhang.cloud.controller;

import com.zhang.cloud.core.Single;
import com.zhang.cloud.core.SingleImpl;
import com.zhang.cloud.entities.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 98549
 * @date 2021/12/24 17:41
 */
@RestController
public class UrlControl {

    @GetMapping("/Single")
    @Single
    public CommonResult getSingle(){
        return new CommonResult(200,"请求成功", SingleImpl.list);
    }
}
