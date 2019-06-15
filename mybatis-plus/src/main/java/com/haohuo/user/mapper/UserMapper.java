package com.haohuo.user.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.haohuo.user.bean.UserModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zhangpk
 * @since 2019-06-14
 */
public interface UserMapper extends BaseMapper<UserModel> {

    List<UserModel> getByWhere(Page<UserModel> page, @Param("user") UserModel userModel);
}
