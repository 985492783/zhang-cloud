package com.zhang.cloud.consumer;

import com.zhang.cloud.entities.CommonResult;

/**
 * @author 98549
 * @date 2022/1/5 18:38
 */
public interface ConsumerService {
    CommonResult invoke(String text);
}
