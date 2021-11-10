package com.zhang.cloud.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author 98549
 * @date 2021/11/6 12:27
 */

@Component
@PropertySource("classpath:/alipay.properties")
@SuppressWarnings("unused")
public class AlipayConfig {
    /**
     * 应用ID
     */
    @Value("${ali_app_id}")
    private String ali_app_id;
    /**
     * 商户私钥
     */
    @Value("${ali_merchant_private_key}")
    private String ali_merchant_private_key;
    /**
     * 商户公钥
     */
    @Value("${ali_merchant_public_key}")
    private String ali_merchant_public_key;
    /**
     * 支付宝公钥
     */
    @Value("${ali_alipay_public_key}")
    private String ali_alipay_public_key;

    /**
     * 服务器异步通知页面路径
     */
    @Value("${ali_notify_url}")
    private String ali_notify_url;
    /**
     * 页面跳转同步通知页面路径
     */
    @Value("${ali_return_url}")
    private String ali_return_url;
    /**
     * 用户付款中途退出返回商户网站的地址
     */
    @Value("${ali_quit_url}")
    private String ali_quit_url;


    /**
     * 签名方式
     */
    @Value("${ali_sign_type}")
    private String ali_sign_type;
    /**
     * 字符编码格式
     */
    @Value("${ali_charset}")
    private String ali_charset;
    /**
     * 支付宝网关
     */
    @Value("${ali_gatewayUrl}")
    private String ali_gatewayUrl;
    /**
     * 角色身份
     */
    @Value("${ali_pid}")
    private String ali_pid;
    /**
     * 过期时间
     */
    @Value("${ali_timeout_express}")
    private String ali_timeout_express;
    public String getAli_app_id() {
        return ali_app_id;
    }
    public String getAli_merchant_private_key() {
        return ali_merchant_private_key;
    }
    public String getAli_merchant_public_key() {
        return ali_merchant_public_key;
    }
    public String getAli_alipay_public_key() {
        return ali_alipay_public_key;
    }
    public String getAli_notify_url() {
        return ali_notify_url;
    }
    public String getAli_return_url() {
        return ali_return_url;
    }
    public String getAli_quit_url() {
        return ali_quit_url;
    }
    public String getAli_sign_type() {
        return ali_sign_type;
    }
    public String getAli_charset() {
        return ali_charset;
    }
    public String getAli_gatewayUrl() {
        return ali_gatewayUrl;
    }
    public String getAli_pid() {
        return ali_pid;
    }
    public String getAli_timeout_express() {
        return ali_timeout_express;
    }
    public void setAli_app_id(String ali_app_id) {
        this.ali_app_id = ali_app_id;
    }
    public void setAli_merchant_private_key(String ali_merchant_private_key) {
        this.ali_merchant_private_key = ali_merchant_private_key;
    }
    public void setAli_merchant_public_key(String ali_merchant_public_key) {
        this.ali_merchant_public_key = ali_merchant_public_key;
    }
    public void setAli_alipay_public_key(String ali_alipay_public_key) {
        this.ali_alipay_public_key = ali_alipay_public_key;
    }
    public void setAli_notify_url(String ali_notify_url) {
        this.ali_notify_url = ali_notify_url;
    }
    public void setAli_return_url(String ali_return_url) {
        this.ali_return_url = ali_return_url;
    }
    public void setAli_quit_url(String ali_quit_url) {
        this.ali_quit_url = ali_quit_url;
    }
    public void setAli_sign_type(String ali_sign_type) {
        this.ali_sign_type = ali_sign_type;
    }
    public void setAli_charset(String ali_charset) {
        this.ali_charset = ali_charset;
    }
    public void setAli_gatewayUrl(String ali_gatewayUrl) {
        this.ali_gatewayUrl = ali_gatewayUrl;
    }
    public void setAli_pid(String ali_pid) {
        this.ali_pid = ali_pid;
    }
    public void setAli_timeout_express(String ali_timeout_express) {
        this.ali_timeout_express = ali_timeout_express;
    }
    @Override
    public String toString() {
        return "AliPayProperties [ali_app_id=" + ali_app_id + ", ali_merchant_private_key=" + ali_merchant_private_key
                + ", ali_merchant_public_key=" + ali_merchant_public_key + ", ali_alipay_public_key="
                + ali_alipay_public_key + ", ali_notify_url=" + ali_notify_url + ", ali_return_url=" + ali_return_url
                + ", ali_quit_url=" + ali_quit_url + ", ali_sign_type=" + ali_sign_type + ", ali_charset=" + ali_charset
                + ", ali_gatewayUrl=" + ali_gatewayUrl + ", ali_pid=" + ali_pid + ", ali_timeout_express="
                + ali_timeout_express + "]";
    }

}