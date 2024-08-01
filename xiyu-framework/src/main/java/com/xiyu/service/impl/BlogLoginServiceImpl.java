package com.xiyu.service.impl;

import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.entity.LoginUser;
import com.xiyu.domain.entity.User;
import com.xiyu.service.BlogLoginService;
import com.xiyu.utils.BeanCopyUtils;
import com.xiyu.utils.JwtUtil;
import com.xiyu.utils.RedisCache;
import com.xiyu.vo.BlogUserLoginVo;
import com.xiyu.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {
    @Autowired
    //定义了认证Authentication的方法
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult login(User user) {
        //使用springSecurity来进行权限登录权限认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        //当前访问系统的用户，封装了用户相关信息。
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //用户名和密码是否通过
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("用户名和密码错误");

        }
        //获取userid生成token
        LoginUser principal = (LoginUser) authenticate.getPrincipal();
        String userId = principal.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);

        //把用户信息存入redis
        redisCache.setCacheObject("blogLogin:" + userId, principal);
        //token和userinfo封装返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(principal.getUser(), UserInfoVo.class);
        //返回的登录请求的vo
        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(jwt, userInfoVo);
        return ResponseResult.okResult(blogUserLoginVo);

    }
}
