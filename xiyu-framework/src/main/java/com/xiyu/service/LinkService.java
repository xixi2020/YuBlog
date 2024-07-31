package com.xiyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.entity.Link;

public interface LinkService extends IService<Link> {
    ResponseResult getAllLink();
}
