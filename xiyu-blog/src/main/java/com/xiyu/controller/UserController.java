package com.xiyu.controller;

import com.xiyu.annotation.SystemLog;
import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.entity.User;
import com.xiyu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/userInfo")
    @SystemLog(bussinessName = "查询个人信息")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    @SystemLog(bussinessName = "更新用户信息")
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }

    @PostMapping("/register")
    @SystemLog(bussinessName = "用户注册")
    public ResponseResult register(@RequestBody User user){
        return userService.register(user);
    }

}
