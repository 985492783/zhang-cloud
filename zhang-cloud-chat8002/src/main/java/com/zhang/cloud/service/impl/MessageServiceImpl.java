package com.zhang.cloud.service.impl;

import com.zhang.cloud.dao.MsgDAO;
import com.zhang.cloud.entities.CommonResult;
import com.zhang.cloud.entities.Message;
import com.zhang.cloud.service.MessageService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 98549
 * @date 2021/11/20 19:17
 */
@Component
public class MessageServiceImpl implements MessageService {
    @Resource
    private MsgDAO msgDAO;
    @Override
    public CommonResult addMsg(Message msg) {
        int val=0;
        try{
            val=msgDAO.insert(msg);
        }catch(Exception e){
            e.printStackTrace();
            return new CommonResult(444,"服务器错误");
        }
        return new CommonResult(200,"插入成功",null);
    }
}
