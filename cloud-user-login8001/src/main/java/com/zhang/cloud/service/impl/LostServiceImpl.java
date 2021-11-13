package com.zhang.cloud.service.impl;

import com.zhang.cloud.dao.LostDAO;
import com.zhang.cloud.entities.CommonResult;
import com.zhang.cloud.entities.LostProperty;
import com.zhang.cloud.service.LostService;
import org.springframework.stereotype.Component;

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
    public CommonResult uploadLost(LostProperty lostProperty) {
        int val=0;
        try {
            val=lostDAO.insert(lostProperty);
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
}
