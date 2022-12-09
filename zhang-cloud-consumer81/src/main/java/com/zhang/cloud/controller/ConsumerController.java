package com.zhang.cloud.controller;

import com.zhang.cloud.aop.Authentication;
import com.zhang.cloud.config.RestTemplateConfig;
import com.zhang.cloud.entities.CommonResult;
import com.zhang.cloud.jna.SystemHardwareInfo;
import com.zhang.cloud.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 98549
 * @date 2021/10/29 17:13
 */
@RestController
@Slf4j
public class ConsumerController{

    private final SystemHardwareInfo systemHardwareInfo=new SystemHardwareInfo();
    @Resource
    private Environment environment;
    @Resource
    private FileService fileService;
    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    private RestTemplateConfig restTemplateConfig;

    @GetMapping("/api/consumer/getCpu")
    public CommonResult test() throws Exception {
        systemHardwareInfo.copyTo();
        return new CommonResult(200,"请求成功",systemHardwareInfo.getCpu());
    }
    @GetMapping("/api/consumer/getMem")
    public CommonResult test2() throws Exception {
        systemHardwareInfo.copyTo();
        return new CommonResult(200,"请求成功",systemHardwareInfo.getMem());
    }

    @GetMapping("/api/consumer/test")
    public CommonResult Tt(){
        return new CommonResult(200,"请求成功",environment.getProperty("hhh"));
    }
    @GetMapping("/api/consumer/orderList")
    public CommonResult getOrderList(HttpServletRequest request){
        return fileService.getOrderList(request.getHeader("token"));
    }
    @GetMapping("/api/consumer/getBean")
    public CommonResult getBean(){
        log.info("server request is {}",restTemplateConfig.getConfig().getServer());
        Object obj = restTemplateConfig.getTemplate().postForObject(restTemplateConfig.getConfig().getServer(), restTemplateConfig.getConfig().getRequest(), Object.class);
        return new CommonResult().success(obj);
    }
}
