package com.zhang.cloud.core;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 98549
 * @date 2021/12/24 17:27
 */
@Aspect
@Component
public class SingleImpl {

    public static List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
    @Resource
    private WebApplicationContext applicationContext;

    @Pointcut("@annotation(com.zhang.cloud.core.Single)")
    private void pointCut(){ }

    @Around(value="pointCut()")
    public Object before(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println(pjp.getSignature().getName());
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
            Map<String, Object> map1 = new HashMap<String, Object>();
            RequestMappingInfo info = m.getKey();
            HandlerMethod method = m.getValue();
            PatternsRequestCondition p = info.getPatternsCondition();
            for (String url : p.getPatterns()) {
                map1.put("url", url);
            }
            Class<?> returnType = method.getMethod().getReturnType();
            Field[] declaredFields = returnType.getDeclaredFields();
            map1.put("className", method.getMethod().getDeclaringClass().getName());
            map1.put("method", method.getMethod().getName());
            Map<String,String> fields =new HashMap<>();
            for(Field field:declaredFields){
                fields.put(field.getName(),field.getGenericType().getTypeName());
            }
            map1.put("returnType",fields);
            RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
            for (RequestMethod requestMethod : methodsCondition.getMethods()) {
                map1.put("type", requestMethod.toString());
            }
            if(map1.get("type")!=null && map1.get("type").equals(RequestMethod.POST.toString())){
                Map<String,Object> mn=new HashMap<>();
                Annotation[][] parameterAnnotations = method.getMethod().getParameterAnnotations();
                Class<?>[] parameterTypes = method.getMethod().getParameterTypes();
                for(int i=0;i<parameterAnnotations.length;i++){
                    for(int j=0;j<parameterAnnotations[i].length;j++){
                        if(parameterAnnotations[i][j].annotationType().getName().equals(RequestBody.class.getName())){
                            Field[] declaredFields1 = parameterTypes[i].getDeclaredFields();
                            for(Field field: declaredFields1){
                                mn.put(field.getName(),field.getGenericType().getTypeName());
                            }
                        }
                    }
                }
                map1.put("RequestBody",mn);
            }
            list.add(map1);
        }
        return pjp.proceed();
    }
}
