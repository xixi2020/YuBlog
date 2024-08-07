package com.xiyu.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserInfoVo {
    /**
     * 许可信息
     */
    private List<String> permissions;

    /**
     * 角色信息
     */
    private List<String> roles;

    /**
     * 用户信息
     */
    private UserInfoVo user;

}
