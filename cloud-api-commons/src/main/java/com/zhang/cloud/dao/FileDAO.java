package com.zhang.cloud.dao;

import com.zhang.cloud.entities.file.Video;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author 98549
 * @date 2021/12/21 0:25
 */
@Mapper
public interface FileDAO {
    @Select("select * from files where id=#{id} limit 1")
    Video getVideoById(@Param("id") Long id);

    @Insert("insert into files values(null,#{Video.file},#{Video.file_name})")
    @Options(keyProperty="Video.id",useGeneratedKeys=true)
    int insert(@Param("Video") Video video);

    @Select("select id,file_name from files")
    List<Video> getMenu();

    @Select("select id,file_name from files where file_name like CONCAT('%',#{name},'%')")
    List<Video> getMenuByName(@Param("name") String name);
}
