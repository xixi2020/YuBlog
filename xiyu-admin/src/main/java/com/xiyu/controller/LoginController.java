package com.xiyu.controller;

import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.entity.LoginUser;
import com.xiyu.domain.entity.Menu;
import com.xiyu.domain.entity.User;
import com.xiyu.enms.AppHttpCodeEnum;
import com.xiyu.exception.SystemException;
import com.xiyu.service.MenuService;
import com.xiyu.service.RoleService;
import com.xiyu.service.SystemLoginService;
import com.xiyu.utils.BeanCopyUtils;
import com.xiyu.utils.SecurityUtils;
import com.xiyu.vo.AdminUserInfoVo;
import com.xiyu.vo.RoutersVo;
import com.xiyu.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LoginController {
    @Autowired
    private SystemLoginService systemLoginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return systemLoginService.login(user);
    }

    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        //获取当前的登录用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        //查询用户角色信息
        List<String> roleKeys = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
        //获取用户信息
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        //封装响应
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, roleKeys, userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);

    }

    @GetMapping("/getRouter")
    public ResponseResult<RoutersVo> getRouter(){
        //获取用户信息
        Long userId = SecurityUtils.getUserId();
        //获取权限菜单
        List<Menu> menuList = menuService.selectRouterMenuTreeByUserId(userId);
        return ResponseResult.okResult(menuList);
    }

    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return systemLoginService.logout();
    }

}
