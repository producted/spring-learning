package com.zhangpk.service;

import com.zhangpk.base.service.IService;
import com.zhangpk.bean.Job;
import org.springframework.cache.annotation.CacheConfig;

import java.util.List;

@CacheConfig(cacheNames = "JobService")
public interface JobService extends IService<Job> {

    Job findJob(Long jobId);

    List<Job> findAllJobs(Job job);

    void addJob(Job job);

    void updateJob(Job job);

    void deleteBatch(String jobIds);

    int updateBatch(String jobIds, String status);

    void run(String jobIds);

    void pause(String jobIds);

    void resume(String jobIds);

}
