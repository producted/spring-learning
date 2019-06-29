## 定时任务是日常开发中非常常见的功能。

对于简单的任务处理Spring的@Scheduled非常好用。  

如果处理更复杂的情况，比如需要宕机恢复或者集群调度，那么Quartz是个不错的轻量级方案。

一些重量级的第三方任务调度系统也是基于Quartz扩展的，比如XXL-JOB，本文直说quartz的实现原理，文末会附上实践代码。

## Quartz的模块

![quartz流程图1](https://producted.github.io/images/posts/java/quartz/流程1.png)

* Trigger定义了何时触发任务，可以说是一个触发器，主要是两种SimpleTrigger和CronTigger,其他Tigger基本都可以通过这两种实现。Trigger还可以定义错过的任务如何处理。下表是说明：

![quartz流程图2](https://producted.github.io/images/posts/java/quartz/流程2.jpeg)

* Calendar与Trigger相反，Calendar定义哪些时间是特例，不能执行任务。Calendar的优先级高于Trigger。HolidayCalendar比较常用，定义了哪些节日是特殊情况。

![quartz流程图3](https://producted.github.io/images/posts/java/quartz/流程3.png)

* Job 负责定义任务所处理的逻辑，实现类需要实现org.quartz.Job接口

```java
public interface Job {
    void execute(JobExecutionContext context) throws JobExecutionException;
}
```

需要留意的是QuartzJobBean抽象类已经默认替我们实现了Job接口，并对工厂进行了一些自动化配置，源码如下：

```java
public abstract class QuartzJobBean implements Job {
    public QuartzJobBean() {
    }
    // execute
    public final void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(this);
            MutablePropertyValues pvs = new MutablePropertyValues();
            pvs.addPropertyValues(context.getScheduler().getContext());
            pvs.addPropertyValues(context.getMergedJobDataMap());
            bw.setPropertyValues(pvs, true);
        } catch (SchedulerException var4) {
            throw new JobExecutionException(var4);
        }

        this.executeInternal(context);
    }

    protected abstract void executeInternal(JobExecutionContext var1) throws JobExecutionException;
}
```

如上，我们应用时可以通过继承QuartzJobBean来并重写executeInternal就可以实现任务配置和记录等操作：

```
public class ScheduleJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) {
        // ...
    }
}
```

具体示例代码在文末给出。

* 通过抛出JobExecutionException可以强制控制任务后续处理

```java
public class JobExecutionException extends SchedulerException {
    private boolean refire = false;//true: 重新执行任务（不会触发下一次）
    private boolean unscheduleTrigg = false;//true: 直接标记Trigger完成
    private boolean unscheduleAllTriggs = false;//true: 直接标记所有和Job相关的Trigger都已经完成
}
```

* Stateful Job。不同于一般的无状态任务，可以同时执行。有状态任务不能同时执行，而且需要保存状态。根据需要给可以Job类添加@PersistJobDataAfterExecution 或 @DisallowConcurrentExecution

```java
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public interface StatefulJob extends Job {
}
```
* JobDetail 保存Job的元信息，包括类定义和设置。

* SchedulerFactory负责初始化，读取配置文件，然后创建Scheduler

* Scheduler是中枢调度器，负责管理Trigger／JobDetail和3个调度线程

## springboot集成quartz

本示例技术栈：

* springboot2.1.6

* quartz2.3.0

* tk.mybatis 实现自动化data操作

上面可以说是说了一堆零件，那么如何使用呢？简单说一下需要留意的地方，springboot1.x和springboot2.x在配置上相比，2.x版本将quartz提供了starter依赖，所以可以通过application内直接配置相关参数即可，具体官网有可以去参考，本文讲的是java代码实现配置，当然依赖和自动配置依赖有所不同，请读者自行区分，不贴一大堆了，quartz核心依赖如下：

```xml
<!--quartz 定时框架-->
        <dependency>
            <groupId>org.quartz-scheduler</groupId>
            <artifactId>quartz</artifactId>
            <version>2.3.0</version>
        </dependency>
```

application.properties配置参考：

```java
server.port=8081

# 数据源配置
spring.datasource.driverClassName=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://dev.51haohuo.net:3306/quartz?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT
spring.datasource.username=root
spring.datasource.password=hc

# mybstis配置
mybatis.mapper-locations=classpath:mapper/*/*.xml
mybatis.type-aliases-package=com.zhangpk.bean
mybatis.configuration.callSettersOnNulls=true
```

核心配置类，注释都在代码内了：

```java
@Configuration
public class ScheduleConfig {

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource) {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setDataSource(dataSource);

		// quartz参数
		Properties prop = new Properties();
		// 任意值 集群节点中必须相同。
		prop.put("org.quartz.scheduler.instanceName", "MyScheduler");
		prop.put("org.quartz.scheduler.instanceId", "AUTO");
		// 线程池配置
		prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
		prop.put("org.quartz.threadPool.threadCount", "20");
		// 优先级
		prop.put("org.quartz.threadPool.threadPriority", "5");
		// JobStore配置
		prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
		// 集群配置
		prop.put("org.quartz.jobStore.isClustered", "true");
		// 属性定义了Scheduler 实例检入到数据库中的频率(单位：毫秒)。
		// Scheduler 检查是否其他的实例到了它们应当检入的时候未检入；这能指出一个失败的 Scheduler 实例，且当前 Scheduler 会以此来接管任何执行失败并可恢复的 Job。
		//通过检入操作，Scheduler 也会更新自身的状态记录。clusterChedkinInterval 越小，Scheduler 节点检查失败的 Scheduler 实例就越频繁。默认值是 15000 (即15 秒)。
		prop.put("org.quartz.jobStore.clusterCheckinInterval", "15000");
		prop.put("org.quartz.jobStore.maxMisfiresToHandleAtATime", "1");

		prop.put("org.quartz.jobStore.misfireThreshold", "12000");
		prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
		factory.setQuartzProperties(prop);

		factory.setSchedulerName("MyScheduler");
		// 延时启动
		factory.setStartupDelay(1);
		factory.setApplicationContextSchedulerContextKey("applicationContextKey");
		// 可选，QuartzScheduler
		// 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
		factory.setOverwriteExistingJobs(true);
		// 设置自动启动，默认为true
		factory.setAutoStartup(true);

		return factory;
	}
}
```

完成这些基础配置之后，我们需要继承上面提到的QuartzJobBean来实现动态任务调用，如下：

```java
public class ScheduleJob extends QuartzJobBean {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ExecutorService service = Executors.newSingleThreadExecutor();

    @Override
    protected void executeInternal(JobExecutionContext context) {
        Job scheduleJob = (Job) context.getMergedJobDataMap().get(Job.JOB_PARAM_KEY);
        long startTime = System.currentTimeMillis();
        try {
            // 执行任务
            logger.info("任务准备执行，任务ID：{}", scheduleJob.getJobId());

            ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(), scheduleJob.getMethodName(),
                    scheduleJob.getParams());
            Future<?> future = service.submit(task);
            future.get();
            long times = System.currentTimeMillis() - startTime;

            logger.info("任务执行完毕，任务ID：{} 总共耗时：{} 毫秒", scheduleJob.getJobId(), times);
        } catch (Exception e) {
            logger.error("任务执行失败，任务ID：" + scheduleJob.getJobId(), e);
            long times = System.currentTimeMillis() - startTime;
        }
    }
}
```

这里注意一点，我们是基于数据库来进行对定时任务的记录，重启恢复的操作的，具体在项目的resource目录下的sql脚本内有建表语句。

另外，由于没有任何页面，所以可以通过接口测试工具来进行调用实现，毕竟前端很弱，有时间的话会补上。

![quartz调用](https://producted.github.io/images/posts/java/quartz/调用图.jpg)

最后我们可以通过线程和quartz提供的删除、暂停、恢复等操作来实现任务调度，通过spring注入要执行的任务类，任务类中写我们要执行的任务就ok了。
