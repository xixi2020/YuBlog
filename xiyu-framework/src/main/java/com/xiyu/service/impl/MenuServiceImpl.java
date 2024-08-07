package com.xiyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiyu.constants.SystemConstants;
import com.xiyu.domain.entity.Menu;
import com.xiyu.mapper.MenuMapper;
import com.xiyu.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    /**
     * 查询用户的权限信息
     * @param id
     * @return
     */
    @Override
    public List<String> selectPermsByUserId(Long id) {
        //是否是管理员，是管理员就返回所有权限
        if (id == 1L){
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            //查询菜单类型为F\C的权限
            queryWrapper.in(Menu::getMenuType, SystemConstants.TYPE_MENU,SystemConstants.TYPE_BUTTON);
            //查询用户状态
            queryWrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            //未被删除的权限
            List<Menu> menus = list(queryWrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }

        //不是管理员，非管理员权限
        List<String> userIds = getBaseMapper().selectPermsByOtherUserId(id);
        return userIds;
    }
}
