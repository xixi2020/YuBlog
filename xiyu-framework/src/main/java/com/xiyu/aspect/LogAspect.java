package com.xiyu.aspect;
import com.xiyu.annotation.SystemLog;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;


/**
 * 日志的切片类
 */
@Aspect
@Component
@Slf4j
public class LogAspect {
    //切点
    @Pointcut("@annotation(com.xiyu.annotation.SystemLog)")
    public void pt(){}

    //环绕方法
    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint){
        //proceed方法表示调用目标方法，ret就是目标方法执行完之后的返回值
        Object ret;

        try {
            handBefore(joinPoint);
            //执行目标方法
            ret = joinPoint.proceed();
            handAfter(ret);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }finally {
            log.info("=======================end=======================" + System.lineSeparator());
        }
        //封装成切面，返回
        return ret;

    }


    /**
     * 执行接口前的操作
     * @param joinPoint
     */
    private void handBefore(ProceedingJoinPoint joinPoint) {
        //获取请求
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        //请求的报文，url、请求方式、ip。
        HttpServletRequest request = requestAttributes.getRequest();

        //获取被增强方法的注解对象
        SystemLog systemlog = getSystemlog(joinPoint);

        log.info("======================Start======================");//下面的几个log输出，第一个参数{}表示占位符，具体的值是第二个参数的变量
        // 打印请求 URL
        log.info("请求URL   : {}",request.getRequestURL());
        // 打印描述信息，例如获取UserController类的updateUserInfo方法上一行的@mySystemlog注解的描述信息
        log.info("接口描述   : {}",systemlog.bussinessName());
        // 打印 Http method
        log.info("请求方式   : {}",request.getMethod());
        // 打印调用 controller 的全路径(全类名)、方法名
        log.info("请求类名   : {}.{}",joinPoint.getSignature().getDeclaringTypeName(),((MethodSignature) joinPoint.getSignature()).getName());
        // 打印请求的 IP
        log.info("访问IP    : {}",request.getRemoteHost());
        // 打印请求入参。JSON.toJSONString十FastJson提供的工具方法，能把数组转成JSON
        log.info("传入参数   : {}", JSON.toJSONString(joinPoint.getArgs()));
    }

    /**
     * 获取到方法对象
     * @param joinPoint
     * @return
     */
    private SystemLog getSystemlog(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        //获取注解
        SystemLog systemlog = methodSignature.getMethod().getAnnotation(SystemLog.class);
        return systemlog;
    }

    private void handAfter(Object ret) {
        // 打印出参。JSON.toJSONString十FastJson提供的工具方法，能把数组转成JSON
        log.info("返回参数   : {}", JSON.toJSONString(ret));
    }

}
