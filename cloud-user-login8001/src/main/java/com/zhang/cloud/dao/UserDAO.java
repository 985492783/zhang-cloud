package com.zhang.cloud.dao;

import com.zhang.cloud.entities.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 98549
 * @date 2021/10/13 11:00
 */
@Mapper
public interface UserDAO {

    int insert(UserDO user);

    UserDO findByUsername(String username);

    int active(String username);

    UserDO findByEmail(String email);
}
