package com.zhang.cloud.service;

import com.zhang.cloud.entities.CommonResult;
import com.zhang.cloud.entities.Order;

/**
 * @author 98549
 * @date 2021/11/6 12:29
 */
public interface AlipayService {
    /**
     *
     * @param order
     * @return
     */
    CommonResult tradeWapPay(Order order);
}
