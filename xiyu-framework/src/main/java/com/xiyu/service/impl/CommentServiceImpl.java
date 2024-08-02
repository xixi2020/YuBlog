package com.xiyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.entity.Comment;
import com.xiyu.mapper.CommentMapper;
import com.xiyu.service.ArticleService;
import com.xiyu.service.CommentService;
import com.xiyu.utils.BeanCopyUtils;
import com.xiyu.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    UserService userService;
    @Override
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        //根据id查评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId, articleId);
        //先查出来根评论,这里先不进行子评论的遍历
        queryWrapper.eq(Comment::getRootId, -1);
        //进行分页
        Page page = new Page(pageNum, pageSize);
        page(page, queryWrapper);
        List records = page.getRecords();
        //格式转换
        List<CommentVo> commentvos = BeanCopyUtils.copyList(records, CommentVo.class);
        //还要获取评论人的名称
        for (CommentVo vo : commentvos ) {
            //获取文章发布者的name
            String nickName = userService.getById(vo.getCreateBy()).getNickName();
            vo.setUserName(nickName);
            //不是根评论，显示评论人的信息
            if (vo.getToCommentUserId()!= -1){
                vo.setToCommentUserName(userService.getById(vo.getToCommentUserId()).getNickName());
            }
        }
        return ResponseResult.okResult(commentvos);
    }
}
