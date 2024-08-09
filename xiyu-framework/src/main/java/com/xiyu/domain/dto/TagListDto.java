package com.xiyu.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 查询tag实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "查询标签的请求参数dto")
public class TagListDto {
    private String name;
    private String remark;
}
