package com.zhang.cloud.service;

import com.zhang.cloud.entities.CommonResult;

/**
 * @author 98549
 * @date 2021/12/26 18:21
 */
public interface FileService {
    CommonResult getOrderList(String token);
}
