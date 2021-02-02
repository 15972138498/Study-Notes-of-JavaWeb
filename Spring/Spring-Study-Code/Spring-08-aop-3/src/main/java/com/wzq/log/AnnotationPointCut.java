package com.wzq.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

//标记这个类是一个切面
@Aspect
public class AnnotationPointCut {
    @Before("execution(* com.wzq.service.UserServiceImpl.*(..))")
    public void before() {
        System.out.println("==========方法执行前==========");
    }

    @After("execution(* com.wzq.service.UserServiceImpl.*(..))")
    public void after() {
        System.out.println("==========方法执行前==========");
    }

    //在环绕增强中，可以给定一个参数，代表想要获取处理切入的点
    @Around("execution(* com.wzq.service.UserServiceImpl.*(..))")
    public void around(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("Around  ==========方法执行前==========");
        Object proceed = jp.proceed();
        Signature signature = jp.getSignature();
        System.out.println(signature);
        System.out.println("Around  ==========方法执行后==========");
    }
}
