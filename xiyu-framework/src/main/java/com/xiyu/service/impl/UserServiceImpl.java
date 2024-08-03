package com.xiyu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.entity.User;
import com.xiyu.mapper.UserMapper;
import com.xiyu.service.UserService;
import com.xiyu.utils.BeanCopyUtils;
import com.xiyu.utils.SecurityUtils;
import com.xiyu.vo.UserInfoVo;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public ResponseResult userInfo() {
        //获取用户id
        Long userId = SecurityUtils.getUserId();
        User user = getById(userId);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }
}
