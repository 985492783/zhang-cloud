package com.zhang.cloud.filter;

import cn.hutool.jwt.JWTUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 98549
 * @date 2021/11/4 21:11
 */
@Component
public class Filter implements GlobalFilter, Ordered {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final String AUTHORIZE_TOKEN = "token";
    private static List<String> list;
    static{
        list = new ArrayList<>();
        list.add("/api/user/login");
    }
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if(!isNeedFilter(request.getPath().value())){
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
        String uid= (String) JWTUtil.parseToken(token).getPayload("uid");
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
            if(uri.equals(u)){
                return false;
            }
        }
        return true;
    }
    @Override
    public int getOrder() {
        return -1;
    }
}
