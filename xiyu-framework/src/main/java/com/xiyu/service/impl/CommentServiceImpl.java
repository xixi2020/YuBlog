package com.xiyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiyu.constants.SystemConstants;
import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.entity.Comment;
import com.xiyu.enms.AppHttpCodeEnum;
import com.xiyu.exception.SystemException;
import com.xiyu.mapper.CommentMapper;
import com.xiyu.service.CommentService;
import com.xiyu.utils.BeanCopyUtils;
import com.xiyu.vo.CommentVo;
import com.xiyu.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    UserService userService;
    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        //根据id查评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();

        //对articleId进行判断:必须commentType为0的时候才增加articleId的判断
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId, articleId);
        //先查出来根评论,这里先不进行子评论的遍历
        queryWrapper.eq(Comment::getRootId, -1);
        //评论类型
        queryWrapper.eq(Comment::getType, commentType);
        //进行分页
        Page page = new Page(pageNum, pageSize);
        page(page, queryWrapper);

        //转换为vo格式
        List<CommentVo> vos = toCommentList(page.getRecords());
        
        //查询子评论
        for (CommentVo vo : vos) {
            List<CommentVo> children = getChildren(vo.getId());
            vo.setChildren(children);
        }
        //返回分页以及评论条数
        return ResponseResult.okResult(new PageVo(vos ,page.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        //评论不可为空
        if (!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    /**
     * 查询根评论的子评论
     * @param id 根评论id
     * @return
     */
    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId, id);
        queryWrapper.orderByDesc(Comment::getCreateTime);
        List<Comment> commentList = list(queryWrapper);
        //
        List<CommentVo> vos = toCommentList(commentList);
        return vos;
    }

    /**
     * 查询未被他人回复的评论并且转换格式
     * @param records
     * @return
     */
    private List<CommentVo> toCommentList(List<Comment> records) {
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
        return commentvos;

    }
}
