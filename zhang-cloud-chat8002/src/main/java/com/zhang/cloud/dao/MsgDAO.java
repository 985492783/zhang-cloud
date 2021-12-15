package com.zhang.cloud.dao;

import com.zhang.cloud.entities.Message;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 98549
 * @date 2021/11/20 18:50
 */
@Mapper
public interface MsgDAO {
    int insert(Message message);
}
