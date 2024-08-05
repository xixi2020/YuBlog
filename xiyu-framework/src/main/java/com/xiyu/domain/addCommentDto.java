package com.xiyu.domain;

import io.swagger.annotations.ApiModel;
import lombok.NoArgsConstructor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "自定义实体类的描述信息-我是专门用于添加评论的DTO实体类")
/**
 * Comment是我们评论表的大类，我们不能在后端使用Comment类去接收前端的请求体
 * DTO就是数据传输对象
 */
public class addCommentDto {

    private Long id;

    //评论类型（0代表文章评论，1代表友链评论）
    private String type;

    //文章id
    @ApiModelProperty(notes = "自定义实体类的字段的描述信息-文章id")
    private Long articleId;

    //根评论id
    private Long rootId;
    //评论内容
    private String content;
    //所回复的目标评论的userid
    private Long toCommentUserId;
    //回复目标评论id
    private Long toCommentId;

    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Date updateTime;

    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;

}