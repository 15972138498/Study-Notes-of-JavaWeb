package com.wzq.log;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

public class AfterLog implements AfterReturningAdvice {
    /*
     * returnValue：返回值
     * method：要执行的目标对象的方法
     * args：参数
     * target：目标对象
     * */
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("执行了" + target.getClass().getName() +
                "中的" + method.getName() + "方法，返回参数是：" + returnValue);
    }
}
