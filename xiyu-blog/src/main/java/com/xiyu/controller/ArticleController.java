package com.xiyu.controller;

import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.entity.Article;
import com.xiyu.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("article")
public class ArticleController{

    @Autowired
    private ArticleService articleService;

    /**
     * 文章查询
     * @return
     */
    @GetMapping("/list")
    public List<Article> test(){
        return articleService.list();
    }

    /**
     * 前十最热文章
     * @return
     */
    @GetMapping("/hotArticles")
    public ResponseResult hotArticleList(){
        return  articleService.hotArticleList();
    }


    /**
     * 分页查询文章
     * @param pageNum 页数
     * @param pageSize 每页查询条数
     * @param categoryIds 分类类型
     * @return
     */
    @GetMapping("")
    public ResponseResult<Article> articleList(Integer pageNum, Integer pageSize, Long categoryIds){

        return articleService.articleList(pageNum, pageSize,categoryIds);

    }
}