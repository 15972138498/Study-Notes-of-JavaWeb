@[toc]

----
# 一、实体类准备
在此之前，应该先创建一个`Spring Boot`程序，[点这些字可以学习如何创建](https://blog.csdn.net/lesileqin/article/details/113792108)

在`com.wzq`包下新建一个实体类`pojo`包，然后创建两个实体类，为了展示`yaml`的语法，这两个类包含几乎所有数据类型，一人一狗：

狗类：
```java
package com.wzq.pojo;

import org.springframework.stereotype.Component;
//指定为组件
@Component
public class Dog {

    private String name;
    private Integer age;
	/*
	toString，有参无参、get、set方法需要写，这里省略
	*/
}
```

人类：
```java
package com.wzq.pojo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

//指定为组件
@Component
//赋值
@ConfigurationProperties(prefix = "person")
public class Person {
    private String name;
    private Integer age;
    private Boolean happy;
    private Date birth;
    private Map<String,Object> maps;
    private List<Object> lists;
    private Dog dog;
	/*
	toString，有参无参、get、set方法需要写，这里省略
	*/
}
```
# 二、编写yaml
```yaml
# #代表注释
person:
  #普通类型，可以直接赋值，注意冒号之后有个空格！
  name: wzq
  age: 21
  happy: false
  birth: 2021/2/11
  maps:
    k1: v1
    k2: v2
  #这里可以写为一行：
  #maps: {k1: v1,k2: v2}

  #数组这样写：
  lists:
    - code
    - music
    - girl
  #数组写为一行：
  #lists: [code,music,girl]
  #对象这样写：
  dog:
    name: 小黑
    age: 3
```
# 三、测试
打开`Spring Boot`的测试类：
```java
package com.wzq;

import com.wzq.pojo.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Springboot02ConfigApplicationTests {

	//自动装配person
    @Autowired
    private Person person;

    @Test
    void contextLoads() {
        System.out.println(person);

    }

}
```
点击运行：
![在这里插入图片描述](https://img-blog.csdnimg.cn/2021021200152743.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
```
Person{
	name='wzq',
	age=21, 
	happy=false, 
	birth=Thu Feb 11 00:00:00 CST 2021, 
	maps={k1=v1, k2=v2}, 
	lists=[code, music, girl], 
	dog=Dog{name='小黑', age=3}}
```
