package com.zhangpk.service;

import com.zhangpk.entity.SysUser;

/**
 * @author zhangpk
 * @date 2019-06-26
 */
public interface UserService {

    SysUser getByUsername(String username);
}
