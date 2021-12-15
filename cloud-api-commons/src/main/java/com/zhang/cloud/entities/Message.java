package com.zhang.cloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author 98549
 * @date 2021/11/20 18:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private Integer id;
    private String nickname;
    private String text;
    private LocalDateTime gmtCreated;
}
