package com.zhang.cloud.consumer;

/**
 * @author 98549
 * @date 2022/1/5 18:42
 */
public interface FactoryService {
    ConsumerService getService(String tag);
}
