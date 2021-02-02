@[toc]

----

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
    <version>1.9.4<ersion>
    <scope>runtime</scope>
</dependency>
```
# 二、自定义类
可以直接定义两个方法，一个之前，一个之后
```java
package com.wzq.log;

public class DiyPointCut {
    public void before(){
        System.out.println("=========方法执行前=========");
    }
    public void after(){
        System.out.println("=========方法执行后=========");
    }
}
```
# 三、配置xml文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        https://www.springframework.org/schema/aop/spring-aop.xsd">

    <!-- 注册bean -->
    <bean id="userService" class="com.wzq.service.UserServiceImpl"/>
    <bean id="diyPointCut" class="com.wzq.log.DiyPointCut" />

    <aop:config>
        <!-- 自定义切面，ref 要引用的类 -->
        <aop:aspect ref="diyPointCut">
            <!-- 切入点 -->
            <aop:pointcut id="point" expression="execution(* com.wzq.service.UserServiceImpl.*(..))"/>
            <!-- 通知 -->
            <aop:before method="before" pointcut-ref="point" />
            <aop:after method="after" pointcut-ref="point" />
        </aop:aspect>
    </aop:config>

</beans>
```
# 四、测试
```java
import com.wzq.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    @Test
    public void Test() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService service = (UserService) context.getBean("userService");
        service.add();
    }
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210202143035492.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
