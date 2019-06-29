package com.zhangpk.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhangpk.bean.Job;
import com.zhangpk.bean.QueryRequest;
import com.zhangpk.bean.ResponseBo;
import com.zhangpk.service.JobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Api(tags = "任务调用相关接口")
public class JobController  {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JobService jobService;

    @GetMapping("job")
    public String index() {
        return "job/job/job";
    }

    @PostMapping("job/list")
    @ResponseBody
    @ApiOperation(value = "查询任务列表")
    public Map<String, Object> jobList(QueryRequest request, Job job) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<Job> list = this.jobService.findAllJobs(job);
        PageInfo<Job> pageInfo = new PageInfo<>(list);
        return getDataTable(pageInfo);
    }

    protected Map<String, Object> getDataTable(PageInfo<?> pageInfo) {
        Map<String, Object> rspData = new HashMap<>();
        rspData.put("rows", pageInfo.getList());
        rspData.put("total", pageInfo.getTotal());
        return rspData;
    }

    @GetMapping("job/checkCron")
    @ResponseBody
    @ApiOperation(value = "检测cron表达式")
    public boolean checkCron(String cron) {
        try {
            return CronExpression.isValidExpression(cron);
        } catch (Exception e) {
            return false;
        }
    }

    @PostMapping("job/add")
    @ResponseBody
    @ApiOperation(value = "创建/新增任务")
    public ResponseBo addJob(Job job) {
        try {
            this.jobService.addJob(job);
            return ResponseBo.ok("新增任务成功！");
        } catch (Exception e) {
            log.error("新增任务失败", e);
            return ResponseBo.error("新增任务失败，请联系网站管理员！");
        }
    }

    @GetMapping("job/delete")
    @ResponseBody
    @ApiOperation(value = "删除任务")
    public ResponseBo deleteJob(String ids) {
        try {
            this.jobService.deleteBatch(ids);
            return ResponseBo.ok("删除任务成功！");
        } catch (Exception e) {
            log.error("删除任务失败", e);
            return ResponseBo.error("删除任务失败，请联系网站管理员！");
        }
    }

    @GetMapping("job/getJob")
    @ResponseBody
    @ApiOperation(value = "根据Id获取任务")
    public ResponseBo getJob(Long jobId) {
        try {
            Job job = this.jobService.findJob(jobId);
            return ResponseBo.ok(job);
        } catch (Exception e) {
            log.error("获取任务信息失败", e);
            return ResponseBo.error("获取任务信息失败，请联系网站管理员！");
        }
    }

    @PostMapping("job/update")
    @ResponseBody
    @ApiOperation(value = "修改任务表")
    public ResponseBo updateJob(Job job) {
        try {
            this.jobService.updateJob(job);
            return ResponseBo.ok("修改任务成功！");
        } catch (Exception e) {
            log.error("修改任务失败", e);
            return ResponseBo.error("修改任务失败，请联系网站管理员！");
        }
    }

    @GetMapping("job/run")
    @ResponseBody
    @ApiOperation(value = "立即执行一次任务")
    public ResponseBo runJob(String jobIds) {
        try {
            this.jobService.run(jobIds);
            return ResponseBo.ok("执行任务成功！");
        } catch (Exception e) {
            log.error("执行任务失败", e);
            return ResponseBo.error("执行任务失败，请联系网站管理员！");
        }
    }

    @GetMapping("job/pause")
    @ResponseBody
    @ApiOperation(value = "暂停任务")
    public ResponseBo pauseJob(String jobIds) {
        try {
            this.jobService.pause(jobIds);
            return ResponseBo.ok("暂停任务成功！");
        } catch (Exception e) {
            log.error("暂停任务失败", e);
            return ResponseBo.error("暂停任务失败，请联系网站管理员！");
        }
    }

    @GetMapping("job/resume")
    @ResponseBody
    @ApiOperation(value = "恢复任务")
    public ResponseBo resumeJob(String jobIds) {
        try {
            this.jobService.resume(jobIds);
            return ResponseBo.ok("恢复任务成功！");
        } catch (Exception e) {
            log.error("恢复任务失败", e);
            return ResponseBo.error("恢复任务失败，请联系网站管理员！");
        }
    }

}
