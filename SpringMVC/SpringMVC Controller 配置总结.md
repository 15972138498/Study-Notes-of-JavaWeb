@[toc]

-----
# 一、搭建环境
## 1、导入maven依赖
使用`Spring MVC`需要导入`servlet`、`jsp`、`jstl`、`spring-webmvc`、`junit`依赖，打开`pom.xml`：
```xml
<dependencies>
    <!-- https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api -->
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>servlet-api</artifactId>
        <version>2.5</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/javax.servlet.jsp/javax.servlet.jsp-api -->
    <dependency>
        <groupId>javax.servlet.jsp</groupId>
        <artifactId>jsp-api</artifactId>
        <version>2.2</version>
    </dependency>
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>jstl</artifactId>
        <version>1.2</version>
    </dependency>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.11</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.2.12.RELEASE</version>
    </dependency>
</dependencies>
```
## 2、在maven项目中添加web框架支持
点击项目名称，点击右键，点击`Add Framework support`，选中`Web Application`，选择版本，最后点击`OK`：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210206090558115.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
这时候，我们创建的`maven`项目中会多出一个`web`目录

然后需要把`web`所需要的依赖包，放入`WEB-INF`文件夹下，选中项目名称，点击左上角的`File`，然后点击`Project Structer`：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210206091213604.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210206091425531.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210206091459364.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
如果是这样：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210206091537222.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
就算添加成功了！
## 3、新建一个页面
在`WEB-INF`下新建一个目录：`jsp`，这里存放`jsp`页面，在这个目录下新建一个`jsp`页面，在这个页面中只做一件事，取后台传来的`msg`：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210206092713287.png)
```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
	${msg}
</body>
</html>
```
## 4、配置Tomcat服务器
点击`idea`右上角的`add Confriguration`：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210206092855709.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210206092950952.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210206093134463.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
然后点击`+`，选择`Artifact`，选择载入哪个项目，修改名字，点击`Apply`，最后点击`OK`：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210206093313653.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
## 5、编写web.xml关联SpringMVC-servlet
打开`web.xml`：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    <!-- 注册DispatcherServlet，这个是SpringMVC的核心，是请求分发器，前端控制器 -->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <!-- 这个类是spring写好的 -->
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!-- 关联一个SpringMVC的配置文件：[servlet-name]-servlet.xml -->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc-servlet.xml</param-value>
        </init-param>
        <!-- 启动级别-1：与服务器一起启动 -->
        <load-on-startup>1</load-on-startup>
    </servlet>

    <!-- /：只匹配所有的请求 不包括.jsp-->
    <!-- /*：匹配所有的请求，包括.jsp -->
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```
这时候，因为还没有写`springmvc-servlet.xml`文件，所以会报红

接下来在`src/main/resources`下新建一个`springmvc-servlet.xml`，这是个`bean`：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

</beans>
```
接下来进入正题：
# 二、使用配置文件【不推荐】
## 1、绑定映射、处理、解析器
使用配置文件搞`SpringMVC`需要在`springmvc-servlet.xml`文件中绑定：处理器映射器，处理器适配器和视图解析器：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- 核心三要素 -->
    <!-- 处理器映射器 -->
    <bean class="org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping"/>
    <!-- 处理器适配器 -->
    <bean class="org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter"/>

    <!-- 视图解析器：DispatcherServlet给他的ModelAndView！不能省略 -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver"
          id = "InternalResourceViewResolver">
        <!-- 前缀 -->
        <property name="prefix" value="/WEB-INF/jsp/" />
        <!-- 后缀 -->
        <property name="suffix" value=".jsp" />
    </bean>

</beans>
```
## 2、实现Controller接口
在`src.main.java.com.wzq.controller`下新建一个`HelloController`，这个`HelloController`需要实现接口`Controller`，重写`handleRequest`方法，并返回一个`ModelAndView`：
```java
package com.wzq.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloController implements Controller {
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        //模型和视图
        ModelAndView mv = new ModelAndView();
        //封装对象，放在ModelAndView中
        mv.addObject("msg","Hello SpringMVC");
        //封装要跳转的视图，放在ModelAndView中
        mv.setViewName("hello");
        return mv;
    }
}
```
最后需要把这个类注册到`bean`，打开`springmvc-servlet.xml`，在最后面添加
```xml
<bean id="/hello" class="com.wzq.controller.HelloController" />
```
## 3、测试
直接点击运行，在浏览器地址栏输入：
```xml
http://localhost:8080/hello
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210206094247927.png)
成功

但是使用配置文件搞`SpringMVC`是很不推荐的，因为要写好多的类实现`Contrlloer`接口，还要注册到`bean`，有点不高级，接下来看使用注解开发`SpringMVC`
# 三、使用注解【推荐】
## 1、使用默认处理器，添加视图解析器
打开`springmvc-servlet.xml`，做四件事：

- 自动扫描`com.wzq.controller`下的包，将其注册为`bean`
- 使用默认处理器
- 开启注解的支持
- 添加视图解析器
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc
        https://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!-- 扫描包 -->
    <context:component-scan base-package="com.wzq.controller"/>
    <!-- 使用默认处理器 -->
    <mvc:default-servlet-handler/>
    <!-- 开启注解支持 -->
    <mvc:annotation-driven/>

    <!-- 视图解析器：DispatcherServlet给他的ModelAndView！不能省略 -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver"
          id = "InternalResourceViewResolver">
        <!-- 前缀 -->
        <property name="prefix" value="/WEB-INF/jsp/" />
        <!-- 后缀 -->
        <property name="suffix" value=".jsp" />
    </bean>
</beans>
```
## 2、写Controller
这里使用`@Controller`和`@RequestMapping`注解：

```java
package com.wzq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

//@Controller注解的类会自动添加到Spring的上下文中
@Controller
public class ControllerTest {

    //映射访问路径
    @RequestMapping("/h1")
    public String index(Model model){
        //Spring MVC 会自动实例化一个Model对象用于视图中传值
        model.addAttribute("msg","hello SpringMVC");
        //返回视图位置
        return "hello";
    }

    /*
    * 还可以配置多个
    * */
}
```
## 3、测试
启动`Tomcat`服务器，在地址栏输入：
```xml
http://localhost:8080/h1
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210206095623739.png)
成功
