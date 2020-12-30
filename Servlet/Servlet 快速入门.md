
@[toc]

-----


# 1、Servlet 简介
什么是`Servlet`，`Servlet`实际上是`Server apple`的缩写，官方给出的定义是运行在服务器端的小程序。

那么为什么要有`Servlet`呢？请看下图：



![](Servlet%20%E7%AE%80%E4%BB%8B.png)




浏览器向服务器请求动态资源，动态资源中一定要有一个`Java类`响应他，而这个类没有`main`方法无法自主执行。也就是说这个类依赖于`Tomcat`执行，所以这个类就要有一定的`规范`才能被`Tomcat`识别，而这个规范就是`Servlet`。

综上所述，**`Servlet`是一个规范，也就是说是一个接口，这个接口定义了这个Java类被Tomcat识别的规则，实现`Servlet`只需要实现接口复写方法即可**。

---
# 2、Servlet 实现步骤
## 1）创建 Java EE 项目
在`IDEA`中创建`Java EE`项目，创建完成之后，如下图所示：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201230161232388.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)

## 2）定义一个类实现 Servlet 接口
上面的图片已经创建过类了，下面只需要在这个类中间实现`Servlet`接口就好了

```java
package wzq.web.servlet;

import javax.servlet.Servlet;

public class ServletDemo1 implements Servlet {
    
}
```

## 3）实现接口中的抽象方法
在`IDEA`中可以可以直接按快捷键`Ctrl + O`选择可重写的方法，我们直接选中如下图所示的五个方法：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201230161820514.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
复写后是这样的：
```java
package wzq.web.servlet;

import javax.servlet.*;
import java.io.IOException;

public class ServletDemo1 implements Servlet {

    /*
    * 初始化方法
    * 在Servlet被创建时执行，只执行一次
    * */
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    /*
    * 获取ServletConfig对象
    * */
    @Override
    public ServletConfig getServletConfig() {
        return null;
    }
    
    /*
    * 提供服务
    * 每一次Servlet被访问时，都会执行，执行N次
    * */
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

    }
    
    /*
    * 获取Servlet版本、作者等信息
    * */
    @Override
    public String getServletInfo() {
        return null;
    }
    /*
    * 销毁
    * 在Servlet对象被杀死时执行，只执行一次
    * */
    @Override
    public void destroy() {

    }
}
```

## 4）配置 Servlet
配置`Servlet`共有两种方式，分别是在`web.xml`中配置和使用`@WebServlet`注解配置。
### a. 在 web.xml 中配置
打开`web.xml`，在`<webapp></web-app>`标签中，写下面格式的xml代码：

```xml
<servlet>
	<servlet-name>名字</servlet-name>
    <servlet-class>Servlet所在类的全类名</servlet-class>
</servlet>
<servlet-mapping>
	<servlet-name>与上面的名字相一致，才能映射到类</servlet-name>
    <url-pattern>虚拟目录</url-pattern>
</servlet-mapping>
```
我是这样写的：

```xml
<servlet>
	<servlet-name>demo1</servlet-name>
    <servlet-class>wzq.web.servlet.ServletDemo1</servlet-class>
</servlet>
<servlet-mapping>
	<servlet-name>demo1</servlet-name>
    <url-pattern>/虚拟目录</url-pattern>
</servlet-mapping>
```
下面是另一种比较简便的配置方式。
### b. 使用注解配置
**需要注意的是：使用注解配置必须保证在`Servlet 3.0`版本以上。**

使用注解配置不需要再`web.xml`中写那么长的代码，只需要在类名上面加上如下面格式的注解：

```java
@WebServlet(urlPatterns = "/虚拟目录")
```
我是这样写的：
```java
@WebServlet(urlPatterns = "/demo2")
```

前面的`urlPatterns`这一部分可以省略，可以这样写：

```java
@WebServlet("/demo2")
```

使用`Servlet`注解配置可以指定多个路径是这样写的：

```java
@WebServlet({"/demo2","/demo22","/demo222"})
```
这个后面可以写N个

还有三种路径定义的规则：

- `/xxx`
- `/xxxx/xxxx`：多级路径、目录
- `*.do`：代表着以`.do`为后缀的文件都可以访问该小程序

---

# 3、Servlet 执行原理
`Servlet`执行原理请看下图：



![](Servlet%20%E6%89%A7%E8%A1%8C%E5%8E%9F%E7%90%86.png)







以下是对上图的每个步骤进行解释：

- `①` ：浏览器发送请求，`web.xml`的`url-pattern`响应
- `②`：获取`servlet-name`标签内容
- `③`：映射到`servlet`标签，匹配`servlet`中的`name`属性
- `④`：找到对应的全类名
- `⑤`：找到对应的类，执行`Servlet`小程序

