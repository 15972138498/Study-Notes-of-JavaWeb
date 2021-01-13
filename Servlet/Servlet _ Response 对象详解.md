@[toc]

----

# 1、写在前面
学`Response`对象，需要先了解`HTTP`协议的响应信息的格式，如果这部分知识还没有了解过，[请点击这些字先行学习HTTP协议](https://blog.csdn.net/lesileqin/article/details/112007609)

[点击这些字学习`Request`对象详解](https://blog.csdn.net/lesileqin/article/details/112059879)

---
# 2、Response 概述
`Respoinse`的功能是：设置响应消息，可以分为以下三类：

- **设置响应行：**  
	* 格式：`HTTP/1.1 200 OK`
	* 设置状态码：`setStatus(int sc)`
- **设置响应头：**`setHeader(String name,String value)`
- **设置响应体**的步骤：
	* 获取输出流：
		+ 字符输出流：`PrintWriter getWriter()`
		+ 字节输出流：`ServletOutputStream getOutputStream()`
	* 使用输出流，将数据输出到客户端浏览器

下面通过四个案例学习`Response`对象的使用

---

# 3、重定向
画一张图理解一下什么是重定向：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210112165448705.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
现在浏览器要访问 A 资源，但是 A 资源发现需要 B 资源才能完成浏览器的要求，于是 A 资源告诉浏览器：你需要访问 B 资源，于是浏览器就去访问 B 资源了，重定向成功。

A 需要告诉浏览器两点信息，分别是状态码`302`和 B 的路径。下面演示一下重定向操作：	 

需求：`ResponseServlet01`重定向到`ResponseServlet02`，分别在两个`Servlet`中打印`1被访问了`和`2被访问了`

在`IDEA`中，新建一个模块，然后新建一个包，再新建两个`Servlet`，分别是`ResponseServlet01`和`ResponseServlet02`
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210112171701442.png)
在`ResponseServlet01`中，打印一句话：

```java
System.out.println("responseServlet01被访问了...");
```
然后设置状态码为`302`：
```java
response.setStatus(302);
```
最后设置响应体`location`：
```java
response.setHeader("location","/ResponseDemo/responseServlet02");
```
同样的，在`ResponseServlet02`中打印：
```java
System.out.println("responseServlet02被访问了...");
```

`ResponseServlet01`代码展示：

```java
package com.wzq.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/responseServlet01")
public class ResponseServlet01 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //打印一句话
        System.out.println("responseServlet01被访问了...");

        //1. 设置状态码为302
        response.setStatus(302);
        //2. 设置响应头location
        response.setHeader("location","/ResponseDemo/responseServlet02");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
```

运行一下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210112172204610.png)
页面重定向成功！

但是我们发现每次重定向都要设置状态码，可不可以省略这个步骤呢？

答：可以，`Response`对象提供了一个重定向的方法：`sendRedirect(String path)`，只需要填一个重定向的路径就可以了，所以上面的两句代码可以替换为：

```java
response.sendRedirect("/ResponseDemo/responseServlet02");
```

下表展示了`重定向`和`请求转发`的**区别**：

| 重定向 | 请求转发 |
| --- | -- |
| 由`Response`调用 | 由`Request`调用 |
| 地址栏发生变化 | 转发地址栏路径不变 |
| 可以访问其他站点的资源 | 只能访问当前服务器下的资源 |
| 两次请求，不能使用`Request`共享数据 | 一次请求，可以使用`Request`共享数据 |
---
# 4、服务器输出字符数据到浏览器
**步骤：**

1. 设置编码
2. 获取字符输出流
3. 输出数据

新建一个类，代码如下：

```java
package com.wzq.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/responseServlet03")
public class ResponseServlet03 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 设置编码
        response.setContentType("text/html;charset=utf-8");
        //2. 获取字符输出流
        PrintWriter pw = response.getWriter();
        //3. 输出数据
        pw.write("<h1>你好，Response</h1>");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
```
输出结果：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210113134035151.png)

---
# 5、服务器输出字节数据到浏览器
一般我们用`字节流`向浏览器输出图片之类的资源，现在只演示输出一些字

步骤：

1. 获取字节输出流
2. 输出数据

新建一个类，代码如下：

```java
package com.wzq.response;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/responseServlet04")
public class ResponseServlet04 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 获取字节输出流
        ServletOutputStream sos = response.getOutputStream();
        //2. 输出数据
        sos.write("你好".getBytes());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
```
输出结果：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210113134933809.png)


