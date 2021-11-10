package com.zhang.cloud.controller;

import com.zhang.cloud.entities.CommonResult;
import com.zhang.cloud.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 98549
 * @date 2021/10/22 18:32
 */
@RestController
@Slf4j
public class BookController {

    @Resource
    private BookService bookService;

    @GetMapping("api/book/page/{id}")
    public CommonResult findPage(@PathVariable("id") Integer id){
        return bookService.findPage(id);
    }


}
