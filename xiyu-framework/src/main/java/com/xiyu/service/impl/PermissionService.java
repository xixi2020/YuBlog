package com.xiyu.service.impl;

import com.xiyu.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ps")
public class PermissionService {
    /**
     * 判断当前用户是否有permission
     * @param permission 要判断的权限
     * @return
     */
    public boolean hasPermission(String permission) {
        if (SecurityUtils.isAdmin()){
            return true;
        }
        List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
        return permissions.contains(permission);
    }
}
