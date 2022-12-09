package com.zhang.cloud.filter;

import cn.hutool.jwt.JWTException;
import cn.hutool.jwt.JWTUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

/**
 * @author 98549
 * @date 2021/11/4 21:11
 */
@Component
public class Filter implements GlobalFilter, Ordered {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisTemplate redisTemplate;
    private static final String AUTHORIZE_TOKEN = "token";
    private static List<String> list;
    static{
        list = new ArrayList<>();
        list.add("/api/user/login");
        list.add("/api/user/verify");
        list.add("/api/user/register");
        list.add("/api/user/sendCode");
        list.add("/api/chat");
        list.add("/api/file");
        list.add("/api/consumer");
    }
    @PostConstruct
    public void init(){
        redisTemplate.opsForHash().putAll("map1",new HashMap<>());
    }
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path=request.getPath().value();
        addRate(path);
        if(!isNeedFilter(path)){
            return chain.filter(exchange);
        }
        HttpHeaders headers = request.getHeaders();
        ServerHttpResponse response = exchange.getResponse();
        String token=headers.getFirst(AUTHORIZE_TOKEN);
        if(StringUtils.isEmpty(token)){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        if(!JWTUtil.verify(token, "zhanghong".getBytes())){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        String uid=null;
        try{
            uid= (String) JWTUtil.parseToken(token).getPayload("uid");
        }catch(JWTException e){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        if(StringUtils.isEmpty(uid)){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        String tok=stringRedisTemplate.opsForValue().get(uid);
        if(!token.equals(tok)){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        return chain.filter(exchange);
    }
    public boolean isNeedFilter(String uri){
        for(String u:list){
            if(uri.contains(u)){
                return false;
            }
        }
        return true;
    }
    @Override
    public int getOrder() {
        return -1;
    }

    public void addRate(String path){
        Queue<Long> queue=(Queue)redisTemplate.opsForHash().get("map1",path);
        if(queue==null){
            queue=create(path);
        }
        queue.add(System.currentTimeMillis());
        redisTemplate.opsForHash().put("map1",path,queue);
    }
    public synchronized Queue create(String path){
        Queue<Long> queue=(Queue)redisTemplate.opsForHash().get("map1",path);
        if(queue==null){
            queue=new LinkedList<>();
            redisTemplate.opsForHash().put("map1",path,queue);
        }
        return queue;
    }
}
