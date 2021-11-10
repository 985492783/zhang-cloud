package com.zhang.cloud.service.impl;

import com.zhang.cloud.dao.BookDAO;
import com.zhang.cloud.entities.BookDO;
import com.zhang.cloud.entities.CommonResult;
import com.zhang.cloud.service.BookService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 98549
 * @date 2021/10/22 18:36
 */
@Component
public class BookServiceImpl implements BookService {

    @Resource
    private BookDAO bookDAO;
    @Override
    public CommonResult findPage(Integer id) {
        Integer num=(id-1)*10;
        List<BookDO> books=bookDAO.findByPage(num);
        if(books.size()==0){
            return new CommonResult(444,"搜索为空",null);
        }
        return new CommonResult(200,"搜索成功",books);
    }
}
