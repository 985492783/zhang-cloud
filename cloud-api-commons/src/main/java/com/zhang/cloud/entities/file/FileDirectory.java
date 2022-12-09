package com.zhang.cloud.entities.file;


/**
 * @author 98549
 * @date 2021/12/19 23:01
 */
public class FileDirectory extends Directory{
    private String suffix;
    public FileDirectory(Long id, Long parentId,Long ownerId, String name, Long depth, FileType type, String suffix){
        super(id,parentId,ownerId,name,depth,type);
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
