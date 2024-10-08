package com.xiyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiyu.constants.SystemConstants;
import com.xiyu.domain.entity.LoginUser;
import com.xiyu.domain.entity.User;
import com.xiyu.mapper.MenuMapper;
import com.xiyu.mapper.UserMapper;
import com.xiyu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(queryWrapper);
        //判断是否查到用户  如果没查到抛出异常
        if(Objects.isNull(user)){
            throw new RuntimeException("用户不存在");
        }
        //后台用户，需要查询权限
        if (user.getType().equals(SystemConstants.IS_ADMAIN)){
            List<String> perms = menuMapper.selectPermsByOtherUserId(user.getId());
            return new LoginUser(user, perms);
        }
        return new LoginUser(user, null);
    }
}
