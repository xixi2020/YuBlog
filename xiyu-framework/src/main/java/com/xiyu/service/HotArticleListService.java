package com.xiyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.entity.Article;

public interface HotArticleListService extends IService<Article> {

    ResponseResult hotArticleList();

}
