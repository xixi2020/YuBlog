package com.xiyu.controller;

import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.entity.Article;
import com.xiyu.service.ArticleService;
import com.xiyu.service.HotArticleListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("article")
public class ArticleController{

    @Autowired
    private HotArticleListService hotArticleListService;

    @Autowired
    private ArticleService articleService;

    @GetMapping("/list")
    public List<Article> test(){
        return articleService.list();
    }

    @GetMapping("/hotArticles")
    public ResponseResult hotArticleList(){
        return  hotArticleListService.hotArticleList();
    }

}
