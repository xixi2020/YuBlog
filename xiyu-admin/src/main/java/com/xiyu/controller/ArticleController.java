package com.xiyu.controller;

import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.dto.AddArticleDto;
import com.xiyu.service.ArticleService;
import com.xiyu.vo.ArticleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 增加博文
     * @param articleDto
     * @return
     */
    @PostMapping("/add")
    public ResponseResult add(@RequestBody AddArticleDto articleDto){
        return articleService.add(articleDto);
    }

}
