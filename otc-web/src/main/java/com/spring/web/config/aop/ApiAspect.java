package com.spring.web.config.aop;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/1/11.
 */
@Aspect
@Component
public class ApiAspect {

    @Pointcut("execution(* com.spring.web.controller.*.*(..))")
    public void allMethod() {
    }

    //前置
    @Before("allMethod()")
    public void beforeExec(JoinPoint joinPoint) {

    }

    //环绕
    @Around("allMethod()")
    public Object aroundExec(ProceedingJoinPoint joinPoint) {
        Object result = null;
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        Method method = ms.getMethod();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String param = JSONObject.toJSONString(request.getParameterMap());  //入参
        String functionId = ms.getMethod().getName(); //功能号
        System.out.println("到此一游");
        try{
            result = joinPoint.proceed();
        }catch (Throwable e){

        }
        return result;
    }

    //后置通知器
    @After("allMethod()")
    public void afterExec(JoinPoint joinPoint) {
    }
}
