package com.zhang.cloud.controller;

import com.zhang.cloud.entities.CommonResult;
import com.zhang.cloud.entities.LostProperty;
import com.zhang.cloud.service.LostService;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 98549
 * @date 2021/11/12 1:02
 */
@RestController
public class LostController {

    @Resource
    private LostService lostService;

    @PostMapping("api/user/upload")
    public CommonResult uploadLostProperty(@RequestBody LostProperty lostProperty, HttpServletRequest request){
        return lostService.uploadLost(lostProperty,request.getHeader("token"));
    }
    @GetMapping("api/user/getAllLostProperty")
    public CommonResult getAllLostProperty(){
        return lostService.getAllLost();
    }
    @GetMapping("api/user/getLostById")
    public CommonResult getLostById(@Param("id") Integer id){
        return lostService.getLostById(id);
    }
    @GetMapping("api/user/searchLost")
    public CommonResult searchLost(@Param("info") String info){
        return lostService.searchLost(info);
    }
    @GetMapping("api/user/getPageNumber")
    public CommonResult getPageNumber(){
        return lostService.getPageNumber();
    }
    @GetMapping("api/user/getLostProperty")
    public CommonResult getLostPropertyByPage(@Param("pageNo") Integer pageNo){
        return lostService.getLostByPage(pageNo);
    }
}
