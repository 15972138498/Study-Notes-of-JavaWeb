`Hello Spring`第一个`Spring`小`Demo`：

第一步：在`idea`中新建一个`Maven`项目，打开`pom.xml`，注入`SpringFreamwork`与`Junit`依赖：

```xml
<dependencies>
    <!-- https://mvnrepository.com/artifact/junit/junit -->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
        <scope>test</scope>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.2.12.RELEASE</version>
    </dependency>
</dependencies>
```
第二步：编写`pojo`实体类：
```java
package com.wzq.pojo;

public class hello {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "hello{" +
                "name='" + name + '\'' +
                '}';
    }
}
```
第三步：编写`beans.xml`配置文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- 
    	一个bean对应一个对象
        对象hello 
        id：名字
        class 要放入bean的类，需要写全类名
    -->
    <bean id="hello" class="com.wzq.pojo.hello">
        <!-- 
            property ： 设置属性
            name：属性的名字
            value：要设置的值（基本数据类型）
            ref：引用Spring已创建的对象
         -->
        <property name="name" value="Hello Spring"/>
    </bean>

</beans>
```
第三步：测试：
```java
import com.wzq.pojo.hello;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    @Test
    public void Test(){
        //获取Spring Context对象
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        //对象都在Spring中管理，要使用可以直接拿
        hello hello =(hello) context.getBean("hello");
        System.out.println(hello);
    }
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210129210133407.png)
`OK！`

对象放在`beans.xml`，用的时候通过`ClassXmlPathXmlApplicationContext`拿就可以了，这个名字好长，可以这样记：`擦C皮P鞋X啊A艹C`

所谓的`Ioc`就是对象由`Spring`创建、管理、分配！
