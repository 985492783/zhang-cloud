package com.zhang.cloud.controller;

import cn.hutool.jwt.JWTException;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSON;
import com.zhang.cloud.config.SpringUtils;
import com.zhang.cloud.entities.Message;
import com.zhang.cloud.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 98549
 * @date 2021/11/19 15:24
 */
@Component
@Slf4j
@ServerEndpoint("/api/chat/{id}")
public class WebSocketController {
    private static ConcurrentHashMap<String, Map<String,Session>> groupMap = new ConcurrentHashMap<>();
    private String nickName;

    @OnOpen
    public void onOpen(@PathParam("id")String id, Session session) {
        Map<String,Session> map = groupMap.computeIfAbsent(id, k -> new HashMap<>());
        String token=session.getQueryString().substring(6);
        try{
            nickName=(String) JWTUtil.parseToken(token).getPayload("nickname");
        }catch(JWTException e){
            System.out.println("验证错误");
        }
        map.put(nickName,session);
        log.info("用户 " + nickName + " 进入聊天室 " + id);
        log.info("共有："+map.size()+"人");
    }

    @OnMessage
    public void OnMessage(String text,@PathParam("id")String id) throws IOException {
        Message msg=new Message();
        msg.setText(text);
        msg.setNickname(nickName);
        MessageService messageService= SpringUtils.getBean("messageServiceImpl");
        messageService.addMsg(msg);
        String json= JSON.toJSONString(msg);
        Map<String,Session> map=groupMap.get(id);
        for(Session session: map.values()){
            session.getBasicRemote().sendText(json);
        }
    }
    @OnClose
    public void OnClose(@PathParam("id") String id){
        Map<String,Session> map=groupMap.get(id);
        map.remove(nickName);
        log.info("用户 " + nickName + " 退出聊天室 " + id);
        log.info("共有："+map.size()+"人");
    }

}
