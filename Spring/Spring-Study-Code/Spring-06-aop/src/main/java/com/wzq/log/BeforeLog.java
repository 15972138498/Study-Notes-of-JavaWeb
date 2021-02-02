package com.wzq.log;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class BeforeLog implements MethodBeforeAdvice {
    /*
     * method：要执行的目标对象的方法
     * args：参数
     * target：目标对象
     * */
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("执行了" + target.getClass().getName() + "，中的" + method.getName() + "方法");
    }
}
