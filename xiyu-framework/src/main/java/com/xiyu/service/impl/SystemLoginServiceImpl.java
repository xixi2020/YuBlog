package com.xiyu.service.impl;

import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.entity.LoginUser;
import com.xiyu.domain.entity.User;
import com.xiyu.service.SystemLoginService;
import com.xiyu.utils.JwtUtil;
import com.xiyu.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
public class SystemLoginServiceImpl implements SystemLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult login(User user) {
        //验证用户名以及密码
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        //用户的认证信息
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //用户密码匹配失败
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或者密码错误");
        }
        //获取userId
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        //生成token
        String jwt = JwtUtil.createJWT(userId);
        //将token放在redis中
        redisCache.setCacheObject("login:"+userId, loginUser);
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("token", jwt);

        //把token返回
        return ResponseResult.okResult(hashMap);
    }
}