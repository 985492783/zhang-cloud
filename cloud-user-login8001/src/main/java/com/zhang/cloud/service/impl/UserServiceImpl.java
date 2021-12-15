package com.zhang.cloud.service.impl;

import cn.hutool.jwt.JWTUtil;
import com.zhang.cloud.dao.UserDAO;
import com.zhang.cloud.entities.CommonResult;
import com.zhang.cloud.entities.UserDO;
import com.zhang.cloud.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


/**
 * @author 98549
 * @date 2021/10/13 11:27
 */
@Component
public class UserServiceImpl implements UserService {
    @Resource
    private UserDAO userDAO;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public CommonResult<UserDO> getUserByUsername(String token) {
        String username = (String) JWTUtil.parseToken(token).getPayload("uid");
        UserDO user = userDAO.findByUsername(username);
        if(user == null){
            return new CommonResult(444,"未找到用户信息",null);
        }else{
            return new CommonResult(200,"找到该用户",user);
        }
    }

    @Override
    public CommonResult login(UserDO userDO) {
        UserDO user=null;
        try{
            user = userDAO.findByUsername(userDO.getUsername());
        }catch(Exception e){
            e.printStackTrace();
            return new CommonResult(444,"服务器超时",null);
        }
        if(user==null){
            return new CommonResult(400,"不存在该用户",null);
        }
        String md5pwd=md5(userDO.getPassword());
        if(!md5pwd.equals(user.getPassword())){
            return new CommonResult(401,"密码错误",null);
        }
        String token = getToken(user.getUsername(),user.getNickname());
        stringRedisTemplate.opsForValue().set(user.getUsername(),token);
        user.setToken(token);
        return new CommonResult(200,"登录成功",user);
    }

    @Override
    public CommonResult logout(String token) {
        stringRedisTemplate.delete((String) JWTUtil.parseToken(token).getPayload("uid"));
        return new CommonResult(200,"登出",null);
    }

    @Override
    public CommonResult verify(String token) {
        String uid = (String) JWTUtil.parseToken(token).getPayload("uid");
        if(StringUtils.isEmpty(uid)){
            return new CommonResult(444,"不存在该用户,请重新登录",null);
        }
        String tok=stringRedisTemplate.opsForValue().get(uid);
        if(token.equals(tok)){
            userDAO.active(uid);
            return new CommonResult(200,"在线",null);
        }else{
            return new CommonResult(444,"该用户不在线,请重新登录",null);
        }
    }

    @Override
    public CommonResult register(UserDO userDO) {
        String val=stringRedisTemplate.opsForValue().get("EMAIL"+userDO.getEmail());
        if(StringUtils.isEmpty(val)){
            return new CommonResult(409,"未发送验证码到该邮箱");
        }
        if(!val.equals(userDO.getToken())){
            return new CommonResult(410,"验证码错误");
        }
        //判断是否创造过
        UserDO user=null;
        try{
            user=userDAO.findByUsername(userDO.getUsername());
            if(user!=null){
                return new CommonResult(411,"该账号已经存在");
            }
            user=userDAO.findByEmail(userDO.getEmail());
            if(user!=null){
                return new CommonResult(412,"该邮箱已注册");
            }
        }catch(Exception e){
            return new CommonResult(444,"服务器超时");
        }
        //密码加密
        String md5p=md5(userDO.getPassword());
        userDO.setPassword(md5p);
        //插入
        try{
            int value=userDAO.insert(userDO);
        }catch(Exception e){
            return new CommonResult(444,"服务器超时");
        }
        return new CommonResult(200,"注册成功");
    }

    public String getToken(String userName,String nickName){
        Map<String, Object> map = new HashMap<String, Object>(){
            {
                put("uid", userName);
                put("loginTime", LocalDateTime.now());
                put("nickname",nickName);
            }
        };
        return JWTUtil.createToken(map, "zhanghong".getBytes());
    }
    public String md5(String password){
        return DigestUtils.md5Hex(password+"_ykd2050").toUpperCase();
    }
}
