package com.xiyu.cornjob;

import com.xiyu.domain.entity.Article;
import com.xiyu.service.ArticleService;
import com.xiyu.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 定时任务：更新redis中的浏览量到mysql中，实现数据同步
 */
@Component
public class UpdateViewCountJob {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleService articleService;
    /**
     * 每过十分钟就将浏览量更新到数据库中，保证数据的一致性
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void updateViewCount(){
        //获取浏览量
        Map<String, Integer> cacheMap = redisCache.getCacheMap("article:viewCount");
        //让双列集合调用entrySet方法即可转为单列集合，然后才能调用stream方法
        List<Article> articles = cacheMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        articleService.updateBatchById(articles);
        //测试
//        System.out.println("redis的文章浏览量数据已更新到数据库，现在的时间是: "+ LocalTime.now());
    }
}
