package com.zhangpk.test;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * @Auther: Zhang Peike
 * @Date: 2019/6/28 11:19
 */
public class TestFirst extends QuartzJobBean {

    private static Logger logger = LoggerFactory.getLogger(TestFirst.class);

    public static void main(String[] args) throws SchedulerException {

        // 1. 创建 SchedulerFactory
        StdSchedulerFactory schedFact = new StdSchedulerFactory();

        // 2. 从工厂中获取调度器实例
        Scheduler scheduler = schedFact.getScheduler();

        // 3. 引进作业程序
        JobDetail jobDetail = JobBuilder.newJob(TestFirst.class).withDescription("this is a ram job") //job的描述
                .withIdentity("jobTest", "jobTestGrip") //job 的name和group
                .build();

        long time=  System.currentTimeMillis() + 3*1000L; //3秒后启动任务
        Date statTime = new Date(time);

        // 4. 创建Trigger
        //使用SimpleScheduleBuilder或者CronScheduleBuilder
        Trigger trigger = TriggerBuilder.newTrigger()
                .withDescription("this is a cronTrigger")
                .withIdentity("jobTrigger", "jobTriggerGroup")
                //.withSchedule(SimpleScheduleBuilder.simpleSchedule())
                .startAt(statTime)  //默认当前时间启动
                .withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?")) //两秒执行一次
                .build();

        // 5. 注册任务和定时器
        scheduler.scheduleJob(jobDetail, trigger);

        // 6. 启动 调度器
        scheduler.start();
        logger.info("启动时间 ： " + new Date());
    }


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.error("JobTest 执行时间: " + new Date());
    }
}
