package com.xiyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.entity.Category;

public interface CategoryService extends IService<Category> {
    ResponseResult getCategoryList();
}
