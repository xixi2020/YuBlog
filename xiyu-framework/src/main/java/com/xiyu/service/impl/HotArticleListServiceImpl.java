package com.xiyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.xiyu.constants.SystemConstants;
import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.entity.Article;
import com.xiyu.mapper.ArticleMapper;
import com.xiyu.service.HotArticleListService;
import com.xiyu.utils.BeanCopyUtils;
import com.xiyu.vo.HotArticleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotArticleListServiceImpl extends ServiceImpl<ArticleMapper, Article> implements HotArticleListService {


    @Override
    public ResponseResult hotArticleList() {
        //使用LambdaQueryWrapper进行查询，可以不用“”进行对比
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        //这里可以使用“”来表示字段，但是这里会有风险，因为编译时无法识别是否是匹配的字段名
        //所以一般是方法引用的方式
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多返回十条
        Page<Article> page = new Page(1,10);
        page(page, queryWrapper);
        List<Article> articleList = page.getRecords();
/*//        bean拷贝变成vo
        for (Article article: articleList) {
            HotArticleVo hotArticleVo = new HotArticleVo();
            BeanUtils.copyProperties(article, hotArticleVo);
            articleVos.add(hotArticleVo);

        }*/
        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyList(articleList, HotArticleVo.class);
        return ResponseResult.okResult(hotArticleVos);
    }
}
