package com.zhang.cloud.entities.file;

import lombok.Data;


/**
 * @author 98549
 * @date 2021/12/19 22:23
 */
@Data
public class Directory {
    private Long id;
    private Long parentId;
    private Long ownerId;
    private String name;
    private Long depth;
    private FileType type;
    private Integer exist;
    public Directory(Long id,Long parentId,Long ownerId,String name,Long depth,FileType type){
        this.id=id;
        this.parentId=parentId;
        this.ownerId=ownerId;
        this.name=name;
        this.depth=depth;
        this.type=type;
        this.exist=1;
    }
    public enum FileType{
        /**
         * 文件夹
         */
        DIRECTORY,
        /**
         * 文件
         */
        FILE
    }
}

