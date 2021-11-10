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
            return new CommonResult(444,"不存在该用户",null);
        }
        String md5pwd=md5(userDO.getPassword());
        if(!md5pwd.equals(user.getPassword())){
            return new CommonResult(444,"密码错误",null);
        }
        String token = getToken(user.getUsername());
        stringRedisTemplate.opsForValue().set(user.getUsername(),token);
        user.setToken(token);
        return new CommonResult(200,"登录成功",user);
    }

    @Override
    public CommonResult logout(String token) {
        stringRedisTemplate.delete((String) JWTUtil.parseToken(token).getPayload("uid"));
        return new CommonResult(200,"登出",null);
    }

    public String getToken(String userName){
        Map<String, Object> map = new HashMap<String, Object>(){
            {
                put("uid", userName);
                put("loginTime", LocalDateTime.now());
            }
        };
        return JWTUtil.createToken(map, "zhanghong".getBytes());
    }
    public String md5(String password){
        return DigestUtils.md5Hex(password+"_ykd2050").toUpperCase();
    }
}
