package com.zhang.cloud.service;

import com.zhang.cloud.entities.CommonResult;

/**
 * @author 98549
 * @date 2021/11/9 18:54
 */
public interface UserService {
    CommonResult findAllUser();

    CommonResult findUserById(Long id);

    CommonResult updateUrlById(Long id,String url);
}
