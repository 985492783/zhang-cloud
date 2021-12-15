package com.zhang.cloud.service.impl;

import com.zhang.cloud.dao.UserDAO;
import com.zhang.cloud.entities.CommonResult;
import com.zhang.cloud.entities.UserDO;
import com.zhang.cloud.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 98549
 * @date 2021/11/9 19:01
 */
@Component
public class UserServiceImpl implements UserService {
    @Resource
    private UserDAO userDAO;
    @Override
    public CommonResult findAllUser() {
        List<UserDO> list=null;
        try{
            list=userDAO.findAllUser();
        }catch(Exception e){
            e.printStackTrace();
            return new CommonResult(444,"服务器异常",null);
        }
        return new CommonResult(200,"查询成功",list);
    }

    @Override
    public CommonResult findUserById(Long id) {
        UserDO userDO=null;
        try {
            userDO=userDAO.findUserById(id);
        }catch(Exception e){
            e.printStackTrace();
            return new CommonResult(444,"服务器超时，请稍后重试",null);
        }
        return new CommonResult(200,"查询成功",userDO);
    }

    @Override
    public CommonResult updateUrlById(Long id,String url) {
        int val=0;
        try{
            val=userDAO.updateUrl(id,url);
        }catch(Exception e){
            e.printStackTrace();
            return new CommonResult(444,"服务器繁忙，请稍后再试",null);
        }
        return new CommonResult(200,"修改成功",null);
    }
}
