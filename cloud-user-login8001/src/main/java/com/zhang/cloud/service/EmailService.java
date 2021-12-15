package com.zhang.cloud.service;

import com.zhang.cloud.entities.CommonResult;

/**
 * @author 98549
 * @date 2021/11/17 21:03
 */
public interface EmailService {
    CommonResult sendCode(String email);
}
