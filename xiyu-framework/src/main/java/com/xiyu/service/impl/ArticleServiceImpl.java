package com.xiyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiyu.constants.SystemConstants;
import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.entity.Article;
import com.xiyu.domain.entity.Category;
import com.xiyu.mapper.ArticleMapper;
import com.xiyu.service.ArticleService;
import com.xiyu.service.CategoryService;
import com.xiyu.utils.BeanCopyUtils;
import com.xiyu.utils.RedisCache;
import com.xiyu.vo.ArticleDetailVo;
import com.xiyu.vo.ArticleListVo;
import com.xiyu.vo.HotArticleVo;
import com.xiyu.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 分页查询文章①只能查询正式发布的文章 ②置顶的文章要显示在最前面
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    @Override
    public ResponseResult<Article> articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        LambdaQueryWrapper<Article> articleQueryWrapper  = new LambdaQueryWrapper<>();
        //查询分类相同
        //这里考虑没有传categoryId的时候，传categoryId才走这一步
        articleQueryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0,
                Article::getCategoryId,categoryId);
        //正式发布的文章
        articleQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //对制定文章进行排序
        articleQueryWrapper.orderByDesc(Article::getIsTop);
        //进行分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, articleQueryWrapper);

        List<Article> articles = page.getRecords();
//        查询分类名称，注意这里的回调方法要返回实体类Article，要在实体类上加链式注解
        articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());
//        for (Article article: articles) {
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }
        //封装结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyList(articles, ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos, page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    /**
     * 查询最热文章
     * @return
     */
    @Override
    public ResponseResult hotArticleList(){
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        articleWrapper.orderByDesc(Article::getViewCount);
        //查询十条
        Page<Article> page = new Page(1, 10);
        page(page, articleWrapper);
        List<Article> pageRecords = page.getRecords();

        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyList(pageRecords, HotArticleVo.class);

        return ResponseResult.okResult(hotArticleVos);
    }

    /**
     * 文章详情
     * @param id
     * @return
     */
    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);

        //从redis里面获取浏览量
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());

        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查找分类名
        Category category = categoryService.getById(articleDetailVo.getCategoryId());
        if (category != null){
            //防止category.getName()空指针
            articleDetailVo.setCategoryName(category.getName());
        }

        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        redisCache.incrementCacheMapValue("article:viewCount",id.toString(),1);

        return ResponseResult.okResult();
    }


}
