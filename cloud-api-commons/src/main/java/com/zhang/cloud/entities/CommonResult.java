package com.zhang.cloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 98549
 * @date 2021/10/13 11:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
    private Integer code;
    private String message;
    private T data;

    public CommonResult(Integer code, String message){
        this(code,message,null);
    }
    public CommonResult success(T data){
        this.code=200;
        this.message="请求成功";
        this.data=data;
        return this;
    }
}
