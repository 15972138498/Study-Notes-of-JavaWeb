@[toc]

---
[Spring 中的 AOP 是什么？](https://blog.csdn.net/lesileqin/article/details/113547039)

# 一、搭建环境
模拟`service`层，首先创建个`UserService`接口：
```java
package com.wzq.service;

public interface UserService {
    void add();
    void delete();
    void update();
    void query();
}
```
`UserService`接口的实现类：
```java
package com.wzq.service;

public class UserServiceImpl implements UserService{
    public void add() {
        System.out.println("增加了一条信息");
    }

    public void delete() {
        System.out.println("删除了一条信息");
    }

    public void update() {
        System.out.println("更新了一条信息");
    }

    public void query() {
        System.out.println("查询了一条信息");
    }
}
```
在`pom.xml`中注入`aspectjweaver`依赖：
```xml
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.9.4</version>
    <scope>runtime</scope>
</dependency>
```
# 二、实现原生API接口
原生`API`接口有五个，如下表所示:
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210202140811612.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
这里实现一下前两个

创建`BeforeLog`类，实现`MethodBeforeAdvice`接口，需要写`before`方法，参数说明都在注释里：
```java
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
```
创建`AfterLog`类，实现`AfterReturningAdvice`接口，写`afterReturning`方法：
```java
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
```
# 三、配置xml文件
在`.xml`中配置`AOP`需要导入约束，直接使用下面这个模板即可：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        https://www.springframework.org/schema/aop/spring-aop.xsd">

</beans>
```
首先需要注册`bean`：
```xml
<!-- 注册bean -->
<bean id="userService" class="com.wzq.service.UserServiceImpl"/>
<bean id="afterLog" class="com.wzq.log.AfterLog"/>
<bean id="beforeLog" class="com.wzq.log.BeforeLog"/>
```
然后是配置`aop`：
```xml
<!-- 方式一：使用原生Spring API接口 -->
<!-- 配置aop：需要导入aop约束 -->
<aop:config>
    <!-- 切入点 -->
    <aop:pointcut id="pointcut" expression="execution(* com.wzq.service.UserServiceImpl.*(..))"/>
    <!-- 执行环绕增加 -->
    <aop:advisor advice-ref="afterLog" pointcut-ref="pointcut" />
    <aop:advisor advice-ref="beforeLog" pointcut-ref="pointcut" />
</aop:config>
```
注意在`aop:pointcut`标签里面有一个`expression`表达式，它的作用就是切入点入口
```xml
expression="execution(修饰符 返回值 包名.类名/接口名.方法名(参数列表))"
```
在上面的例子中，忽略掉了修饰符，`*`表示所有，比如`com.wzq.service.UserServiceImpl.*(..))`，它的意思是：`com.wzq.service.UserServiceImpl`类下所有的方法和所有的参数
# 四、测试
编写测试类：
```java
import com.wzq.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    @Test
    public void Test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService service = (UserService) context.getBean("userService");
        service.add();
    }
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210202141559350.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
