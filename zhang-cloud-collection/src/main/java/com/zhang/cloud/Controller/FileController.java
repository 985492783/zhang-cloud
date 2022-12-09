package com.zhang.cloud.Controller;

import com.zhang.cloud.dao.FileDAO;
import com.zhang.cloud.entities.CommonResult;
import com.zhang.cloud.entities.file.Video;
import com.zhang.cloud.service.FileService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 98549
 * @date 2021/12/19 23:09
 */
@RestController
public class FileController {

    @Resource
    private FileService fileService;
    @Resource
    private FileDAO fileDAO;
    @GetMapping("/api/file/test")
    public CommonResult getFile(){
        return fileService.getFile();
    }

    @GetMapping("/api/file/getById/{id}")
    public CommonResult getFileById(@PathVariable("id") Long id){
        return fileService.getDepthFile(id);
    }

    @GetMapping("/api/file/getVideo/{id}")
    public CommonResult getVideo(@PathVariable("id") Long id){
        return new CommonResult(200,"获取成功",fileDAO.getVideoById(id));
    }

    @PostMapping("/api/file/upload")
    public CommonResult Insert(@RequestBody Video video){
        return fileService.collect(video);
    }

    @GetMapping("/api/file/getMenu")
    public CommonResult getMenu(){
        return new CommonResult(200,"获取成功",fileDAO.getMenu());
    }

    @GetMapping("/api/file/getMenuByName/{name}")
    public CommonResult getMenuByName(@PathVariable("name") String name){
        return new CommonResult(200,"获取成功",fileDAO.getMenuByName(name));
    }
}
