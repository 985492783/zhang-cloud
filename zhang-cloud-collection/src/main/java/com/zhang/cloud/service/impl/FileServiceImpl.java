package com.zhang.cloud.service.impl;

import com.zhang.cloud.dao.FileDAO;
import com.zhang.cloud.entities.CommonResult;
import com.zhang.cloud.entities.file.Directory;
import com.zhang.cloud.entities.file.FileDirectory;
import com.zhang.cloud.entities.file.Video;
import com.zhang.cloud.service.FileService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 98549
 * @date 2021/12/19 23:14
 */
@Component
public class FileServiceImpl implements FileService {
    @Resource
    private FileDAO fileDAO;
    @Override
    public CommonResult getFile() {
        List<Directory> list = new ArrayList<>();
        list.add(new FileDirectory(1L,0L,2L,"test",1L, Directory.FileType.FILE,"pdf"));
        list.add(new Directory(2L,0L,2L,"学习资料",1L,Directory.FileType.DIRECTORY));
        return new CommonResult(200,"获取成功",list);
    }

    @Override
    public CommonResult getDepthFile(Long id) {
        List<Directory> list = new ArrayList<>();
        if(id==0) {
            return getFile();
        }
        if(id==1){
            list.add(new FileDirectory(3L,1L,2L,"杰哥不要啊",1L, Directory.FileType.FILE,"txt"));
            list.add(new Directory(4L,1L,2L,"test",1L,Directory.FileType.DIRECTORY));
        }else if(id==2){
            list.add(new FileDirectory(5L,2L,2L,"数据结构",1L, Directory.FileType.FILE,"doc"));
            list.add(new Directory(6L,2L,2L,"学习",1L,Directory.FileType.DIRECTORY));
            list.add(new Directory(7L,2L,2L,"电影资源",1L,Directory.FileType.DIRECTORY));
        }
        return new CommonResult(200,"响应成功",list);
    }

    @Override
    public CommonResult collect(Video video) {
        int i=0;
        try {
            i=fileDAO.insert(video);
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult(444,"接口异常",null);
        }
        return new CommonResult(200,"状态",i);
    }
}
