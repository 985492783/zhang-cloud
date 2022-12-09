package com.zhang.cloud.config;

import cn.hutool.core.bean.BeanUtil;
import com.sun.tools.javac.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 98549
 * @date 2022/1/16 19:44
 */
@ConfigurationProperties(prefix = "zhang.cloud")
public class TestConfigProperties {
    @Resource
    private Req req;
    private String ip;
    private String port;
    private String serverName;
    private String params;

    public String getServer() {
        return "http://" + getIp() + ":" + getPort() + getServerName();
    }

    public HttpEntity<MultiValueMap> getRequest(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        // 设置请求参数
        MultiValueMap<String, Object> postParameters=req.getMultiValueMap();
        return new HttpEntity<>(postParameters, headers);
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        System.out.println(1);
        this.ip = ip;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public static class Req implements ApplicationListener<WebServerInitializedEvent> {
        public  Req(){
            InetAddress localHost = null;
            try{
                localHost = Inet4Address.getLocalHost();
            }catch(Exception e){
                e.printStackTrace();
            }
            this.hostname = localHost.getHostName();
            this.ip = localHost.getHostAddress();
            this.v = System.getProperty("java.version");
            this.version = System.currentTimeMillis();
        }
        @Value("${spring.application.name}")
        private String app;
        private String hostname;
        private String ip;
        private Integer port;
        private String v;
        private Long version;

        @Override
        public void onApplicationEvent(WebServerInitializedEvent webServerInitializedEvent) {
            this.port = webServerInitializedEvent.getWebServer().getPort();
        }

        public String getApp() {
            return app;
        }

        public void setApp(String app) {
            this.app = app;
        }

        public String getHostname() {
            return hostname;
        }

        public void setHostname(String hostname) {
            this.hostname = hostname;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public String getV() {
            return v;
        }

        public void setV(String v) {
            this.v = v;
        }

        public Long getVersion() {
            return version;
        }

        public void setVersion(Long version) {
            this.version = version;
        }

        public MultiValueMap<String,Object> getMultiValueMap(){
            MultiValueMap<String,Object> map =new LinkedMultiValueMap<>();
            map.put("app", List.of(this.app));
            map.put("hostname",List.of(this.hostname));
            map.put("ip",List.of(this.ip));
            map.put("port",List.of(this.port));
            map.put("v",List.of(this.v));
            map.put("version",List.of(this.version));
            return map;
        }
    }
}
