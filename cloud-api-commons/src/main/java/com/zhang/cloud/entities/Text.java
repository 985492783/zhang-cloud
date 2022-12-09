package com.zhang.cloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 98549
 * @date 2022/1/10 19:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Text {
    private Type type;
    private String message;
    private Object data;

    public enum Type{
        /**
         * 消息通知
         */
        NOTIFY,
        /**
         * 警告 （报错）
         */
        WARNING,
        /**
         * 订单信息
         */
        ORDER;
    }
}
