package com.xiyu.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接收后台前端响应的实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    //分类名
    private String name;
    //描述
    private String description;
    //状态0:正常,1禁用
    private String status;

}