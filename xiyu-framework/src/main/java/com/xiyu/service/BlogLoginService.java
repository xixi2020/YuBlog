package com.xiyu.service;

import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);
}
