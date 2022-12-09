package com.zhang.cloud.process;

import cn.hutool.core.util.ClassUtil;
import com.zhang.cloud.aop.Authentication;
import com.zhang.cloud.controller.MyTest;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;

import static java.lang.System.out;

/**
 * @author 98549
 * @date 2021/12/31 17:50
 */
@Component
public class MyProcess implements SmartInitializingSingleton, ApplicationContextAware{
    private ApplicationContext ctx;
    @Override
    public void afterSingletonsInstantiated() {
        Set<Class<?>> classes = ClassUtil.scanPackage("");
        classes.stream().forEach(e->{
            Arrays.stream(e.getDeclaredFields()).filter(f ->f.getAnnotation(Authentication.class)!=null)
                    .forEach(f->{
                        try{
                            f.setAccessible(true);
                            Object obj = ctx.getBean(e);
                            Object val = ctx.getBean(f.getType());
                            f.set(obj,val);
                        }catch(Exception ex){
                            ex.printStackTrace();
                        }
                    });
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
