package com.xiyu.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 写博客:增加标签实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "新增标签的请求参数dto")
public class TabListDto {
    private String name;
    private String remark;
}
