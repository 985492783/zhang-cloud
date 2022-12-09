package com.zhang.cloud.consumer;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author 98549
 * @date 2022/1/5 18:43
 */
@Component
public class FactoryServiceImpl implements FactoryService, ApplicationContextAware {
    private ApplicationContext applicationContext;
    @Override
    public ConsumerService getService(String tag) {
        if(tag.equals("createOrder")){
            return applicationContext.getBean(OrderConsumer.class);
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
