package com.zhangpk.mapper;

import com.zhangpk.base.mapper.MyMapper;
import com.zhangpk.bean.Job;

import java.util.List;

public interface JobMapper extends MyMapper<Job> {
	
	List<Job> queryList();
}