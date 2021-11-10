package com.zhang.cloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 98549
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDO implements Serializable {
    private Long id;

    private String bookName;

    private String author;

    private Boolean exist;

    private String url;

    private LocalDateTime gmtCreated;


}
