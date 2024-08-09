package com.xiyu.domain.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
@Data
@AllArgsConstructor
@SuppressWarnings("serial")
@NoArgsConstructor
@TableName("sg_tag")
/**
 * 标签(Tag)实体类
 *
 * @author makejava
 * @since 2024-08-06 14:57:34
 */
public class Tag{
    @TableId
    private Long id;
    /**
     * 标签名
     */
    private String name;

    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    private Integer delFlag;
    /**
     * 备注
     */
    private String remark;


}

