package com.xiyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiyu.constants.SystemConstants;
import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.entity.Article;
import com.xiyu.mapper.ArticleMapper;
import com.xiyu.service.ArticleService;
import com.xiyu.service.CategoryService;
import com.xiyu.utils.BeanCopyUtils;
import com.xiyu.vo.HotArticleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private CategoryService categoryService;

    /**
     * 分页查询文章①只能查询正式发布的文章 ②置顶的文章要显示在最前面
     * @param pageNum
     * @param pageSize
     * @param categoryIds
     * @return
     */
    @Override
    public ResponseResult<Article> articleList(Integer pageNum, Integer pageSize, Long categoryIds) {
        //

        return null;
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


}
