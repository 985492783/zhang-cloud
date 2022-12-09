package com.zhang.cloud.config;

import cn.hutool.jwt.JWTException;
import cn.hutool.jwt.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 98549
 * @date 2022/1/5 20:39
 */
@Component
@Slf4j
@ServerEndpoint("/api/message")
public class MessageService {
    private static ConcurrentHashMap<Long, Session> groupMap = new ConcurrentHashMap<>();
    private Long id;

    @OnOpen
    public void onOpen(Session session) {
        String token=session.getQueryString().substring(6);
        try{
            id=Long.valueOf(JWTUtil.parseToken(token).getPayload("id").toString());
        }catch(JWTException e){
            System.out.println("JWT格式不正确");
            return;
        }
        groupMap.put(id,session);
        log.info("*** {} 监听成功",id);
    }
    @OnClose
    public void onClose(){
        groupMap.remove(id);
        log.info("*** {} 退出监听",id);
    }
    public static void send(Long id,String text){
        Session session=groupMap.get(id);
        if(session==null){
            return;
        }
        try{
            session.getBasicRemote().sendText(text);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
