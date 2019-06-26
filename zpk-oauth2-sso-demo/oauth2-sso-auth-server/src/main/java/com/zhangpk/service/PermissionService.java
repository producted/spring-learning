package com.zhangpk.service;

import com.zhangpk.entity.SysPermission;

import java.util.List;

/**
 * @author zhangpk
 * @date 2019-06-26
 */
public interface PermissionService {

    List<SysPermission> findByUserId(Integer userId);

}
