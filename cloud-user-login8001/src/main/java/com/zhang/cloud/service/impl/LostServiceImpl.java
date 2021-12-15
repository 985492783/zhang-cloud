package com.zhang.cloud.service.impl;

import cn.hutool.jwt.JWTUtil;
import com.zhang.cloud.dao.LostDAO;
import com.zhang.cloud.entities.CommonResult;
import com.zhang.cloud.entities.LostProperty;
import com.zhang.cloud.service.LostService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 98549
 * @date 2021/11/12 1:08
 */
@Component
public class LostServiceImpl implements LostService {
    @Resource
    private LostDAO lostDAO;
    @Override
    public CommonResult uploadLost(LostProperty lostProperty,String token) {
        int val=0;
        String username= (String) JWTUtil.parseToken(token).getPayload("uid");
        try {
            val=lostDAO.insert(lostProperty);
            if(val>0){
                val=lostDAO.insertUnite(lostProperty.getId(),username);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult(444,"服务器超时，请稍后重试",null);
        }
        return new CommonResult(200,"插入成功",null);
    }

    @Override
    public CommonResult getAllLost() {
        List<LostProperty> list=null;
        try{
            list=lostDAO.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult(444,"服务器超时，请稍后重试",null);
        }
        return new CommonResult(200,"请求成功",list);
    }

    @Override
    public CommonResult getLostById(Integer id) {
        LostProperty lostProperty=null;
        try {
            lostProperty=lostDAO.findLostById(id);
        }catch(Exception e){
            e.printStackTrace();
            return new CommonResult(444,"服务器超时，请稍后重试",null);
        }
        return new CommonResult(200,"查找成功",lostProperty);
    }

    @Override
    public CommonResult searchLost(String info) {
        List<LostProperty> list = null;
        try {
            list = lostDAO.findLostByInfo(info);
        }catch(Exception e){
            e.printStackTrace();
            return new CommonResult(444,"服务器超时，请稍后重试",null);
        }
        return new CommonResult(200,"查询成功",list);
    }

    @Override
    public CommonResult getPageNumber() {
        Integer res=0;
        try {
            res=lostDAO.getCount();
            res=(res+9)/10;
        }catch(Exception e){
            e.printStackTrace();
            return new CommonResult(444,"服务器错误，请稍后重试",0);
        }
        return new CommonResult(200,"查询成功",res);
    }

    @Override
    public CommonResult getLostByPage(Integer page) {
        List<LostProperty> list= null;
        if(page<=0){
            return new CommonResult(444,"非法查询",null);
        }
        Integer num=(page-1)*10;
        try{
            list = lostDAO.getLostByPage(num);
        }catch(Exception e){
            e.printStackTrace();
            return new CommonResult(444,"服务器错误，请稍后重试",null);
        }
        return new CommonResult(200,"请求成功",list);
    }
}
