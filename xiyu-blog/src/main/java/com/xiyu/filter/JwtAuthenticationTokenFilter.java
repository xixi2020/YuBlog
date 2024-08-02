package com.xiyu.filter;

import com.alibaba.fastjson.JSON;
import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.entity.LoginUser;
import com.xiyu.enms.AppHttpCodeEnum;
import com.xiyu.utils.JwtUtil;
import com.xiyu.utils.RedisCache;
import com.xiyu.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 登录校验过滤器
 *
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头中的token
        String token = httpServletRequest.getHeader("token");
        //没有携带token，说明不需要登录，直接放行
        if (!StringUtils.hasText(token)){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        //Claims:身份认证和授权中存储和传递用户信息
        Claims claims = null;
        try {
            //解析token
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            //token非法或者超时，重新登录
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            //告诉前端要重新登录
            WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
            return;
        }
        //获取userId
        String userId = claims.getSubject();
        //获取用户信息
        LoginUser loginUser = redisCache.getCacheObject("blogLogin:" + userId);
        //获取不到信息，说明token过期,需要重新登录
        if (Objects.isNull(loginUser)){
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
            return;
        }
        //存入上下文
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //过滤器处理完后需要放行
        filterChain.doFilter(httpServletRequest, httpServletResponse);


    }
}
