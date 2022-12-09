package com.zhang.cloud.service;

import com.zhang.cloud.entities.CommonResult;
import com.zhang.cloud.entities.file.Video;

/**
 * @author 98549
 * @date 2021/12/19 23:13
 */
public interface FileService {
    CommonResult getFile();
    CommonResult getDepthFile(Long id);
    CommonResult collect(Video video);
}
