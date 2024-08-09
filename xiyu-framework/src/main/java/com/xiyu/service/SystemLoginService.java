package com.xiyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.entity.User;

public interface SystemLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
