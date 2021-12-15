package com.zhang.cloud.service;

import com.zhang.cloud.entities.CommonResult;
import com.zhang.cloud.entities.UserDO;

/**
 * @author 98549
 * @date 2021/10/13 11:27
 */
public interface UserService {
    /**
     * getUserByUsername
     * @param username
     * @return CommonResult
     */
    CommonResult getUserByUsername(String username);

    /**
     * 登录页面
     * @param userDO
     * @return CommonResult
     */
    CommonResult login(UserDO userDO);

    /**
     * 登出
     * @param token
     * @return CommonResult
     */
    CommonResult logout(String token);

    /**
     * 验证
     * @param token
     * @return CommonResult
     */
    CommonResult verify(String token);

    /**
     * 注册
     * @param userDO
     * @return
     */
    CommonResult register(UserDO userDO);
}
