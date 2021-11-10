package com.zhang.cloud.dao;

import com.zhang.cloud.entities.BookDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 98549
 * @date 2021/10/22 18:36
 */
@Mapper
public interface BookDAO {
    List<BookDO> findByPage(Integer num);
}
