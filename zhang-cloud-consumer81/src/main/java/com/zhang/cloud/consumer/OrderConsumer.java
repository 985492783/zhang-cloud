package com.zhang.cloud.consumer;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhang.cloud.config.MessageService;
import com.zhang.cloud.entities.CommonResult;
import com.zhang.cloud.entities.Order;
import com.zhang.cloud.entities.SnowFlake;
import com.zhang.cloud.entities.Text;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 98549
 * @date 2022/1/5 18:38
 */
@Component
@Slf4j
public class OrderConsumer implements ConsumerService{
    @Resource
    private MongoTemplate mongoTemplate;
    @Override
    public CommonResult invoke(String text) {
        Order order = JSONUtil.toBean(text, Order.class);
        order.setOrderId(SnowFlake.nextId());
        order.setExist(1);
        System.out.println(order.getOrderId());
        try{
            MessageService.send(order.getUserId(), new ObjectMapper().writeValueAsString(new Text(Text.Type.NOTIFY,"订单："+order.getOrderId()+"下单成功",order)));
        }catch(Exception e){
            e.printStackTrace();
        }
        Order result = mongoTemplate.save(order, "order");
        return new CommonResult().success(result);
    }
}
