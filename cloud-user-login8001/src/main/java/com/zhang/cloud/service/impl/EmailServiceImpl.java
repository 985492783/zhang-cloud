package com.zhang.cloud.service.impl;

import com.zhang.cloud.entities.CommonResult;
import com.zhang.cloud.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author 98549
 * @date 2021/11/17 21:04
 */
@Component
public class EmailServiceImpl implements EmailService {
    @Resource
    private JavaMailSender mailSender;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Value("${sendEmailAddress}")
    private String sendEmailAddress;

    @Override
    public CommonResult sendCode(String email) {
        if (StringUtils.isEmpty(email)) {
            return new CommonResult(444,"邮箱不能为空",null);
        }
        SimpleMailMessage message = new SimpleMailMessage();
        if(StringUtils.isEmpty(sendEmailAddress)){
            return new CommonResult(444,"发件人为空",null);
        }
        int code=(int)((Math.random()*9+1)*100000);
        message.setFrom(sendEmailAddress);
        message.setTo(email);
        message.setSubject("验证码");
        message.setText(String.valueOf(code));
        stringRedisTemplate.opsForValue().set("EMAIL"+email,String.valueOf(code),5*60000, TimeUnit.MILLISECONDS);
        try {
            mailSender.send(message);
        }catch(Exception e){
            e.printStackTrace();
            return new CommonResult(478,"不正确的邮箱",null);
        }
        return new CommonResult(200,"发送成功",null);
    }
}
