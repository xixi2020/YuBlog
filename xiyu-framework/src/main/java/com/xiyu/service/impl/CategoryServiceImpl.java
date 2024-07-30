package com.xiyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiyu.constants.SystemConstants;
import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.entity.Article;
import com.xiyu.domain.entity.Category;
import com.xiyu.mapper.CategoryMapper;
import com.xiyu.service.ArticleService;
import com.xiyu.service.CategoryService;
import com.xiyu.utils.BeanCopyUtils;
import com.xiyu.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    ArticleService articleService;
    @Override
    public ResponseResult getCategoryList() {
        //查询已经发布的文章
        LambdaQueryWrapper<Article>articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);

        //查询已发布文章的分类表：并且是去重之后的结果
        Set<Long> categorys = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());
        //查询分类表,分类表的状态也得是正常
        List<Category> categoryList = listByIds(categorys);
        List<Category> categories = categoryList.stream()
                .filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装在vo中
        List<CategoryVo> categoryVos = BeanCopyUtils.copyList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

}
