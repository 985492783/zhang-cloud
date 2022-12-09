package com.zhang.cloud.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {
    private Long userId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderId;
    private String amount;
    private String subject;
    private String status;
    private Integer exist;
}
