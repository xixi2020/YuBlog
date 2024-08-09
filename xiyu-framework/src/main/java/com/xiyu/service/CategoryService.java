package com.xiyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.entity.Category;
import com.xiyu.vo.AdminCategoryVo;

import java.util.List;

public interface CategoryService extends IService<Category> {
    //查询文章分类
    ResponseResult getCategoryList();

    //写博客查询接口：查询文章分类
    List<AdminCategoryVo> listAllCategory();

}
