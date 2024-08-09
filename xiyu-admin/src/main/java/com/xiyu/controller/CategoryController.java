package com.xiyu.controller;

import com.xiyu.domain.ResponseResult;
import com.xiyu.service.CategoryService;
import com.xiyu.vo.AdminCategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 写博文查询文章接口
     * @return
     */
    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        List<AdminCategoryVo> list = categoryService.listAllCategory();
        return ResponseResult.okResult(list);
    }
}