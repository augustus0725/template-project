package com.sabo.entity.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangcanbin@hongwangweb.com
 * @date 2022/7/28 16:12
 */
@Data
public class TeacherId implements Serializable {
    private Integer id;
    private String zone;
}
