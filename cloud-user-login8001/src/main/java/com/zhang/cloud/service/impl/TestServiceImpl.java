package com.zhang.cloud.service.impl;

import com.zhang.cloud.dao.BookDAO;
import com.zhang.cloud.entities.BookDO;
import com.zhang.cloud.entities.CommonResult;
import com.zhang.cloud.service.TestService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author 98549
 * @date 2021/11/28 20:08
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class TestServiceImpl implements TestService {

    @Resource
    private BookDAO bookDAO;
    @Override
    public CommonResult A() {
        BookDO bookDO = bookDAO.findBookById(1L);
        if(bookDO.getExist() != 0){
            return new CommonResult(444,"已被借走",null);
        }
        try{
            Thread.sleep(5000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        int val=bookDAO.updateBook(bookDO.getId());
        return new CommonResult(200,"A成功"+val,bookDO);
    }

    @Override
    public CommonResult B() {
        BookDO bookDO = bookDAO.findBookById(1L);
        if(bookDO.getExist() != 0){
            return new CommonResult(444,"已被借走",null);
        }
        try{
            Thread.sleep(5000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        int val=bookDAO.updateBook(bookDO.getId());
        return new CommonResult(200,"B成功"+val,bookDO);
    }
}
