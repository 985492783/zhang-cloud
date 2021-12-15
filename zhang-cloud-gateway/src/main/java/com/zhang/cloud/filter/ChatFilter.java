package com.zhang.cloud.filter;

import cn.hutool.jwt.JWTException;
import cn.hutool.jwt.JWTUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @author 98549
 * @date 2021/11/19 18:09
 */
@Component
public class ChatFilter implements GlobalFilter, Ordered {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        if(!request.getPath().value().contains("/api/chat")){
            return chain.filter(exchange);
        }
        String token=request.getQueryParams().getFirst("token");
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
        String tok= stringRedisTemplate.opsForValue().get(uid);
        if(!token.equals(tok)){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 3;
    }
}
