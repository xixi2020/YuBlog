package com.xiyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiyu.domain.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role> {
    //查询用户的角色信息
    List<String> selectRoleKeyByUserId(Long id);
}
