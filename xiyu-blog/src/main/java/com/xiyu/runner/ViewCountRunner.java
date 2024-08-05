package com.xiyu.runner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiyu.domain.entity.Article;
import com.xiyu.mapper.ArticleMapper;
import com.xiyu.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 启动类：项目启动时就将博客的浏览量和id字段存储在redis中
 */
@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public void run(String... args) throws Exception {
        //查询博客浏览量
        List<Article> articles = articleMapper.selectList(null);
        //用map接收文章id和浏览量，注意类型的转换
        Map<String, Integer> collect = articles.stream().collect(Collectors.toMap(article -> article.getId().toString(),
                article -> {
                    return article.getViewCount().intValue();
                }));
        //存入redis中
        redisCache.setCacheMap("article:viewCount" ,collect);
    }
}
