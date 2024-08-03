package com.xiyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.entity.Comment;

public interface CommentService extends IService<Comment> {
    /**
     * 更具传参来查询评论
     * @param commentType 友链：1 本站：0
     * @param articleId 友链：null
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}
