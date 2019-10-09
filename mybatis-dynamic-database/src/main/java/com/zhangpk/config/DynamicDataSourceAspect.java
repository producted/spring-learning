package com.zhangpk.config;

import com.zhangpk.util.DataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Auther: Zhang Peike
 * @Date: 2019/7/23 10:44
 */
@Aspect
@Component
@Order(-1) //需要加入切面排序
public class DynamicDataSourceAspect {
    private Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    //切入点只对@Service注解的类上的@DataSource方法生效
    @Pointcut(value="@within(org.springframework.stereotype.Service) && @annotation(DataSourceTarget)" )
    public void dynamicDataSourcePointCut(){}

    @Before(value = "dynamicDataSourcePointCut()")
    public void switchDataSource(JoinPoint joinpoint) throws Throwable{
        MethodSignature signature = (MethodSignature)joinpoint.getSignature();
        Method method = signature.getMethod();
        DataSourceTarget dataSourceTarget = method.getAnnotation(DataSourceTarget.class);
        logger.info("##############数据源 ：{}###############",dataSourceTarget.value());
        DataSourceContextHolder.setDataSource(dataSourceTarget.value());
    }

    @After(value="dynamicDataSourcePointCut()")
    public void after(){
        DataSourceContextHolder.cleanDataSource();
    }
}
