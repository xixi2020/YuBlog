package com.xiyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.dto.AddArticleDto;
import com.xiyu.domain.entity.Article;
import org.springframework.stereotype.Service;

public interface ArticleService extends IService<Article> {
    ResponseResult<Article> articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult hotArticleList();

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    //新增博客文章
    ResponseResult add(AddArticleDto article);
}
