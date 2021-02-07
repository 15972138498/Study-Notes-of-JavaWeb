@[toc]

-----

在开始之前，应该先新建一个`maven`工程，注入`SpringMVC`以及`Servlet`依赖，再使用注解配置一下`Controller`，[可以点击这些字查看详细配置过程！](https://blog.csdn.net/lesileqin/article/details/113709550)

# 一、接收请求参数
## 1、提交的域名称和处理方法的参数名一致
首先在前台的`index.jsp`写一个表单，然后提交一个`username`：

```html
<html>
<head>
    <title>$Title$</title>
</head>
<body>

<form method="post" action="/test/">
    <input name="username">
    <br>
    <input type="submit">
</form>

</body>
</html>
```
新建一个`Controller`，处理前台来的数据，返回到`hello.jsp`页面（这是个空页面）：
```java
package com.wzq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControllerTest2 {

    @RequestMapping("/test")
    public String Test1(String username){
        System.out.println(username);
        return "hello";
    }
}
```
运行`Tomcat`，在`index.jsp`页面的表单里输入：`wzq`
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210207083825467.png)
点击提交，查看后台：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210207083838611.png)
通过上面的案例，知道了一点：就是前台`input`的`name`属性和`Controller`参数名称一样的时候，可以直接读取到
## 2、提交的域名称和处理方法的参数名不一致
如果不一致怎么呢？会出现什么情况？把上面的`Controller`修改一下：
```java
@RequestMapping("/test")
public String Test1(String name){
    System.out.println(name);
    return "hello";
}
```
只把参数名称修改了一下，和前台的不一样了！运行，打印一下:
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210207084208798.png)
可以看到，只输出了`null`，证明没有匹配到，遇到这种情况，只需要在`Controller`的方法的参数里加个注解`@RequestParam("对应前台的name属性")`就好了，修改一下：
```java
@RequestMapping("/test")
public String Test1(@RequestParam("username") String name){
    System.out.println(name);
    return "hello";
}
```
输入数据，运行：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210207083838611.png)
## 3、提交的是一个对象
这个要求提交的表单域和对象的属性名称一致，参数使用对象即可。如果使用对象，但是参数名称不一致，则匹配不到，对应名称不一致的字段都为`null`

新建一个`User`的`pojo`类：
```java
package com.wzq.pojo;

public class User {

    private int id;
    private String name;
    private int age;
	
	/*
		get/set
		toString
		...
		省略
	*/
}

```
修改一个前端的表单：
```html
<form method="post" action="/test/">
    id：<input name="id">
    <br>
    name：<input name="name">
    <br>
    age：<input name="age">
    <br>
    <input type="submit">
</form>
```
对应的后台：
```java
@RequestMapping("/test")
public String Test2(User user){
    System.out.println(user);
    return "hello";
}
```
运行：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210207085528539.png)
点击提交：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210207085545968.png)
OK了
