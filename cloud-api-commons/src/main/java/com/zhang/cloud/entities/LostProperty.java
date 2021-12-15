package com.zhang.cloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author 98549
 * @date 2021/11/12 0:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LostProperty {
    /**
     * 物品id
     */
    private Integer id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 信息
     */
    private String info;
    /**
     * 地点
     */
    private String location;
    /**
     * qq
     */
    private String qq;
    /**
     * wx
     */
    private String wx;
    /**
     * image
     */
    private String image;
    /**
     * time
     */
    private LocalDateTime gmtCreated;

    private String type;

    private Integer publish;

    private Integer exist;
}
