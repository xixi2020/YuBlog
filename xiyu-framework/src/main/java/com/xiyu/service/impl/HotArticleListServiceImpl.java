package com.xiyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.entity.Article;
import com.xiyu.mapper.ArticleMapper;
import com.xiyu.service.HotArticleListService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotArticleListServiceImpl extends ServiceImpl<ArticleMapper, Article> implements HotArticleListService {


    @Override
    public ResponseResult hotArticleList() {
        //使用LambdaQueryWrapper进行查询，可以不用“”进行对比
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus,0);
        //进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多返回十条
        Page<Article> page = new Page(1,10);
        page(page, queryWrapper);
        List<Article> articleList = page.getRecords();
        return ResponseResult.okResult(articleList);
    }
}
