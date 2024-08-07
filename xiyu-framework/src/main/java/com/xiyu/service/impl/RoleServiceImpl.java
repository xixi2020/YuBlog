package com.xiyu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiyu.domain.entity.Role;
import com.xiyu.mapper.RoleMapper;
import com.xiyu.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //是否是管理员
        if (id == 1L){
            ArrayList<String> roleKey = new ArrayList<>();
            roleKey.add("admin");
            return roleKey;
        }
        //普通用户的角色信息
        List<String> roles= getBaseMapper().selectRoleKeyByOtherUserId(id);
        return roles;
    }
}
