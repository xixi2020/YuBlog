package com.xiyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiyu.domain.entity.Menu;

import java.util.List;

public interface MenuService extends IService<Menu> {
    //查询用户的权限信息
    List<String> selectPermsByUserId(Long id);
    //查询用户的路由信息，也就是查询权限菜单
    List<Menu> selectRouterMenuTreeByUserId(Long userId);

}
