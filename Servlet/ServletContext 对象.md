@[toc]

-----
# 一、ServletContext 概述
`ServletContext`概念：代表整个`web`应用，可以和程序的容器（服务器）来通信

共有两种获取`ServletContext`对象的方式：

- 通过`request`对象获取：`request.getServletContext();`
- 通过`HttpServlet`对象获取：`this.getServletContext();`
# 二、功能

## 1、获取 MIME 类型
`MIME`类型即在互联网通信过程中定义的一种文件数据类型

它的格式是：`大类型/小类型`，例如：`text/html`或者`image/jpeg`

通过方法`String getMimeType(String file)`来获取

如下代码演示了如何获取`MIME`类型：

```java
@WebServlet("/contextDemo01")
public class ContextDemo01 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 通过HttpServlet来获取ServletContext对象
        ServletContext context = this.getServletContext();
        //2. 定义一个文件的名称
        String filename = "a.jpg";  // image/jpeg
        //3.获取MIME类型
        String mimeType = context.getMimeType(filename);
        System.out.println(mimeType);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
```
输出结果：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210113162121519.png)
## 2、域对象：共享数据
关于域对象，我在[之前的博客](https://blog.csdn.net/lesileqin/article/details/112527458)里面提到过，`ServletContext`与`request`对象的设置域对象、获取域对象、移除域对象的方法一模一样：

- 设置域对象：`setAttribute(String name,Object value)`
- 获取域对象：`getAttribute(String name)`
- 移除域对象：`removeAttribute(String name)`

但是`ServletContext`域对象的范围包括所有用户请求的数据，它的生命周期从服务器开启一直到服务器关闭才可以结束，而且所有用户都可以操控，这也意味着`ServletContext`对象不安全，用的时候千万要慎重！

下面演示一下：

新建一个`Servlet`，这里写设置域对象，写入以下代码：
```java
@WebServlet("/contextDemo02")
public class ContextDemo02 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 获取ServletContext对象
        ServletContext context = this.getServletContext();
        //2. 设置域对象
        context.setAttribute("msg","I am here!");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
```
再新建一个`Servlet`，这里获取域对象：
```java
@WebServlet("/contextDemo03")
public class ContextDemo03 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 获取ServletContext对象
        ServletContext context = this.getServletContext();
        //2. 获取域对象
        Object msg = context.getAttribute("msg");
        System.out.println(msg);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
```
代码完成，启动`Tomcat`服务器，然后首先访问第一个`Servlet`，接着访问第二个`Servlet`，输出结果如下所示：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210113163613207.png)
## 3、获取文件真实（服务器）路径   
首先我们在项目里面创建三个文件，分别放在不同的位置，如下图所示：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210113164511903.png)
然后新建一个`Servlet`，写代码：

```java
@WebServlet("/contextDemo04")
public class ContextDemo04 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 获取ServletContext对象
        ServletContext context = this.getServletContext();
        //2. 获取web目录下的资源访问，b.txt
        String bpath = context.getRealPath("/b.txt");
        System.out.println("b.txt路径：" + bpath);
        //3. 获取WEB-INF目录下的资源访问 c.txt
        String cpath = context.getRealPath("/WEB-INF/c.txt");
        System.out.println("c.txt路径：" + cpath);
        //4. 获取src目录下的资源访问 a.txt
        String apath = context.getRealPath("/WEB-INF/classes/a.txt");
        System.out.println("a.txt路径：" + apath);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}

```
输出结果：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210113164553588.png)
这样就输出出来啦~
