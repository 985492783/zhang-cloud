package com.zhang.cloud.service;

import com.zhang.cloud.entities.CommonResult;
import com.zhang.cloud.entities.Order;

/**
 * @author 98549
 * @date 2022/1/5 14:51
 */
public interface RocketmqService {
    CommonResult sendAsync(Order order);
    CommonResult sendSync(String str);
}
