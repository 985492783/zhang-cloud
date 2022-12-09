package com.zhang.cloud.service.impl;

import cn.hutool.jwt.JWTException;
import cn.hutool.jwt.JWTUtil;
import com.zhang.cloud.entities.CommonResult;
import com.zhang.cloud.entities.Order;
import com.zhang.cloud.service.FileService;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 98549
 * @date 2022/1/11 3:48
 */
@Component
public class FileServiceImpl implements FileService {
    @Resource
    private MongoTemplate mongoTemplate;
    @Override
    public CommonResult getOrderList(String token) {
        Long id=null;
        try{
            id=Long.valueOf(JWTUtil.parseToken(token).getPayload("id").toString());
        }catch(JWTException e){
            return new CommonResult();
        }
        Query query=new Query(Criteria.where("userId").is(id).and("exist").is(1)).with(Sort.by("orderId").descending());
        query.fields().include("orderId").include("amount").include("subject").include("status");
        List<Order> order = mongoTemplate.find(query, Order.class, "order");
        return new CommonResult().success(order);
    }
}
