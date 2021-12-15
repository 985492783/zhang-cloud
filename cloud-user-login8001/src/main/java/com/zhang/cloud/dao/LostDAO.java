package com.zhang.cloud.dao;

import com.zhang.cloud.entities.LostProperty;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 98549
 * @date 2021/11/12 1:08
 */
@Mapper
public interface LostDAO {
    int insert(LostProperty lostProperty);

    List<LostProperty> findAll();

    LostProperty findLostById(Integer id);

    List<LostProperty> findLostByInfo(String info);

    Integer getCount();

    List<LostProperty> getLostByPage(Integer num);

    int insertUnite(Integer lostId,String username);
}
