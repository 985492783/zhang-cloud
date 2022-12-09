package com.zhang.cloud.entities.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 98549
 * @date 2021/12/21 0:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    private Long id;
    private String file;
    private String file_name;
}
