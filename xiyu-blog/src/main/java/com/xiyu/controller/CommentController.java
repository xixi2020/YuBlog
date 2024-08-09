package com.xiyu.controller;

import com.xiyu.annotation.SystemLog;
import com.xiyu.constants.SystemConstants;
import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.dto.addCommentDto;
import com.xiyu.domain.entity.Comment;
import com.xiyu.service.CommentService;
import com.xiyu.utils.BeanCopyUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestBody注解接收前端传过来的请求体
@ApiModel(description = "添加评论的实体类")
@Api(tags = "评论的相关接口文档", description = "我是描述信息")
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize){
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT, articleId, pageNum, pageSize);
    }
    @GetMapping("/linkCommentList")
    @ApiOperation(value = "友链评论列表",notes = "获取友链评论区的评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "每页大小")
    })
    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize){
        return commentService.commentList(SystemConstants.LINK_COMMENT, null, pageNum, pageSize);
    }

    @PostMapping("/addComment")
    @SystemLog(bussinessName = "在文章评论区发送评论")//接口描述，用于'日志记录'功能
    public ResponseResult addComment(@RequestBody addCommentDto addCommentDto){
        Comment comment = BeanCopyUtils.copyBean(addCommentDto, Comment.class);
        return commentService.addComment(comment);
    }
}
