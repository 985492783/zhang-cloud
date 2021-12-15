package com.zhang.cloud.service;

import com.zhang.cloud.entities.CommonResult;
import com.zhang.cloud.entities.Message;

/**
 * @author 98549
 * @date 2021/11/20 19:17
 */
public interface MessageService {
    CommonResult addMsg(Message msg);
}
