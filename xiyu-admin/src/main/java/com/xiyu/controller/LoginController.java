package com.xiyu.controller;

import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.entity.User;
import com.xiyu.enms.AppHttpCodeEnum;
import com.xiyu.exception.SystemException;
import com.xiyu.service.SystemLoginService;
import com.xiyu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private SystemLoginService systemLoginService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return systemLoginService.login(user);
    }



}
