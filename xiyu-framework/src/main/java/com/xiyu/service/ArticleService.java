package com.xiyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.entity.Article;
import org.springframework.stereotype.Service;

public interface ArticleService extends IService<Article> {
    ResponseResult<Article> articleList(Integer pageNum, Integer pageSize, Long categoryIds);

    ResponseResult hotArticleList();
}
