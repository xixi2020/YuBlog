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
 * jwt生成token
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //获取请求头中的token值
        String token = request.getHeader("token");
        //判断上面那行有没有拿到token值
        if(!StringUtils.hasText(token)){
            //说明该接口不需要登录，直接放行，不拦截
            filterChain.doFilter(request,response);
            return;
        }
        //解析获取的token，把原来的密文解析为原文
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            //当token过期或token被篡改就会进入下面那行的异常处理
            e.printStackTrace();
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        String userid = claims.getSubject();

        LoginUser loginUser = redisCache.getCacheObject("login:" + userid);
        //如果在redis获取不到值，说明登录是过期了，需要重新登录
        if(Objects.isNull(loginUser)){
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }

        //把从redis获取到的value，存入到SecurityContextHolder(Security官方提供的类)
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request,response);

    }
}