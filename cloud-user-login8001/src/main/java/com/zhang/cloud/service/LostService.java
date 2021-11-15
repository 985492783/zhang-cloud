package com.zhang.cloud.service;

import com.zhang.cloud.entities.CommonResult;
import com.zhang.cloud.entities.LostProperty;

/**
 * @author 98549
 * @date 2021/11/12 1:07
 */
public interface LostService {

    CommonResult uploadLost(LostProperty lostProperty);

    CommonResult getAllLost();

    CommonResult getLostById(Integer id);

    CommonResult searchLost(String info);

    CommonResult getPageNumber();

    CommonResult getLostByPage(Integer page);
}
