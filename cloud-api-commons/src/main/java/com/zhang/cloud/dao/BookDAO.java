package com.zhang.cloud.dao;

import com.zhang.cloud.entities.BookDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author 98549
 * @date 2021/11/28 20:09
 */
@Mapper
public interface BookDAO {

    @Select("select id,name,author,exist,url from book where id=#{id} FOR UPDATE")
    BookDO findBookById(Long id);

    @Update("update book set exist=1 where id=#{id}")
    int updateBook(Long id);
}
