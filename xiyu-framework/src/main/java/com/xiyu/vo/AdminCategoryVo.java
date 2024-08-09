package com.xiyu.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 后台系统的分类查询
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
//返回给前端的特定字段
public class AdminCategoryVo {

    private Long id;
    private String name;
    //描述
    private String description;

}