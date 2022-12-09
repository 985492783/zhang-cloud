package com.zhang.cloud.service.impl;

import cn.hutool.json.JSONUtil;
import com.zhang.cloud.entities.CommonResult;
import com.zhang.cloud.entities.Order;
import com.zhang.cloud.service.RocketmqService;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 98549
 * @date 2022/1/5 14:51
 */
@Component
public class RocketmqServiceImpl implements RocketmqService {
    private final Logger log= LoggerFactory.getLogger(RocketmqServiceImpl.class);
    @Resource
    private DefaultMQProducer defaultMQProducer;

    @Override
    public CommonResult sendAsync(Order order) {
        String str = JSONUtil.toJsonStr(order);
        Message msg = new Message("shop","createOrder",str.getBytes());
        try{
            defaultMQProducer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("消息为{}",sendResult);
                }

                @Override
                public void onException(Throwable e) {
                    e.printStackTrace();
                }
            });
        }catch(Exception e){
            e.printStackTrace();
            return new CommonResult(400,"请求失败");
        }
        return new CommonResult(200,"请求成功");
    }

    @Override
    public CommonResult sendSync(String str) {
        Message sendMsg = new Message("TestTopic", "TestTag", str.getBytes());
        SendResult result =null;
        try{
            result=defaultMQProducer.send(sendMsg);
        }catch(Exception e){
            e.printStackTrace();
            return new CommonResult(400,"请求失败");
        }
        return new CommonResult(200,"请求成功",result);
    }
}
