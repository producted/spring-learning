package com.haohuo.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.haohuo.user.bean.UserModel;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhangpk
 * @since 2019-06-14
 */
public interface UserService extends IService<UserModel> {


    List<UserModel> getByWhere(Page<UserModel> page, UserModel userModel);
}
