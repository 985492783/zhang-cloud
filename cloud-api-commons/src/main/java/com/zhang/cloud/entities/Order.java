package com.zhang.cloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 98549
 * @date 2021/11/8 14:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String userId;
    private String orderId;
    private String amount;
    private String subject;
    private String status;
    private Integer exist;
}
