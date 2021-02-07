@[toc]


----

在开始之前，应该先新建一个maven工程，注入SpringMVC以及Servlet依赖，再使用注解配置一下Controller，[可以点击这些字查看详细配置过程！](https://blog.csdn.net/lesileqin/article/details/113709550)

# 一、基本使用
导入`Jackson`的包：
```xml
<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.12.1</version>
</dependency>
```
下面看如何使用：
```java
package com.wzq.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wzq.utils.JsonUtil;
import com.wzq.pojo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Controller
@RestController //这个注解标注下列方法，不走视图解析器，只返回字符串
public class JacksonTest {

    @RequestMapping("/j1")
    //@ResponseBody
    // 加上这个注解，会直接返回一个字符串
    public String Test1() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        User user = new User(1,"wzq",18);
        return mapper.writeValueAsString(user);
    }

    //集合
    @RequestMapping("/j2")
    public String Test2() throws JsonProcessingException {
        User user1 = new User(1,"wzq1",18);
        User user2 = new User(2,"wzq2",18);
        User user3 = new User(3,"wzq3",18);
        User user4 = new User(4,"wzq4",18);
        List<User> list = new ArrayList<User>();
        list.add(user1);
        list.add(user2);
        list.add(user3);
        list.add(user4);
        //可以直接写为一行
        return new ObjectMapper().writeValueAsString(list);
    }

    //时间
    @RequestMapping("/j3")
    public String Test3() throws JsonProcessingException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return new ObjectMapper().writeValueAsString(sdf.format(date));
    }

}
```
# 二、json乱码问题解决
只需要在`web.xml`中加入以下代码就可以了：
```xml
<!-- JSON乱码问题配置 -->
<mvc:annotation-driven>
    <mvc:message-converters register-defaults="true">
        <bean class="org.springframework.http.converter.StringHttpMessageConverter">
            <constructor-arg value="UTF-8"/>
        </bean>
        <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
            <property name="objectMapper">
                <bean class="org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean">
                    <property name="failOnEmptyBeans" value="false"/>
                </bean>
            </property>
        </bean>
    </mvc:message-converters>
</mvc:annotation-driven>
```
# 三、抽取为工具类
```java
package com.wzq.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;

public class JsonUtil {

    public static String getJson(Object object) {
        return getJson(object, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getJson(Object object, String dateFormat) {
        ObjectMapper mapper = new ObjectMapper();
        //不使用自定义时间戳的方式
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //自定义日期的格式
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        mapper.setDateFormat(sdf);
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
```
