package com.zhang.cloud.dao;

import com.zhang.cloud.entities.UserDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 98549
 * @date 2021/11/9 18:54
 */
@Mapper
public interface UserDAO {
    List<UserDO> findAllUser();

    UserDO findUserById(Long id);

    int updateUrl(Long id,String url);
}
