package com.zhangpk.vo;

import com.zhangpk.entity.SysUser;
import lombok.Data;

import java.util.List;

/**
 * 扩展用，懒得搞了这里这个类也没个毛用
 *
 * @author zhangpk
 * @date 2019-06-26
 */
@Data
public class SysUserVO extends SysUser {

    /**
     * 权限列表
     */
    private List<String> authorityList;

}
