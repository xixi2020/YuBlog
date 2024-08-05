package com.xiyu.controller;

import com.xiyu.annotation.SystemLog;
import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.entity.Article;
import com.xiyu.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * 分页查询文章,如果直接是从请求体当中得到的比如articleList/1,就要加@param注解
     * @param pageNum 页数
     * @param pageSize 每页查询条数
     * @param categoryId 分类类型
     * @return
     */
    @GetMapping("/articleList")
    public ResponseResult<Article> articleList(Integer pageNum, Integer pageSize, Long categoryId){

        return articleService.articleList(pageNum, pageSize,categoryId);

    }

    @GetMapping("/{id}")
    public  ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }

    @PutMapping("/updateViewCount/{id}")
    @SystemLog(bussinessName = "根据id查询文章浏览量")
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);

    }
}