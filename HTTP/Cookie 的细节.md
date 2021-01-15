@[toc]

---

# 一、一次可不可以发送多个 cookie
答：可以！可以创建多个`cookie`对象，使用`response`调用多次`addCookie`方法发送`cookie`即可，下面实验一下：

新建两个`HttpServlet`，在第一个`HttpServlet`中发送`Cookie`：

```java
@WebServlet("/cookieDemo3")
public class CookieDemo3 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //新建三个cookie，并通过response发送出去
        Cookie cookie1 = new Cookie("msg","hello");
        Cookie cookie2 = new Cookie("name","zhangsan");
        Cookie cookie3 = new Cookie("sex","male");
        response.addCookie(cookie1);
        response.addCookie(cookie2);
        response.addCookie(cookie3);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
```
第二个`HttpServer` ，接受`cookie`，并打印到控制台：

```java
@WebServlet("/cookieDemo2")
public class CookieDemo2 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //3. 获取Cookie，拿到数据
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                String value = cookie.getValue();
                System.out.println(name + " : " + value);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
```
运行服务器，控制台输出如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210115160938956.png)

# 二、cookie 在浏览器中保存多长时间

在默认情况下，当浏览器关闭后，`Cookie`数据被销毁。

但是`Cookie`可以设置持久化存储，他有一个`setMaxAge(int seconds)`方法

这个方法的参数，有三种情况：

- 正数：将`Cookie`数据写到硬盘的文件中，进行持久化存储，这个正的参数就是`cookie`存活的时间
- 负数：即默认值，没有太大的意义
- 零：删除`Cookie`的信息

下面演示一下参数为正数的情况：

新建一个`HttpServlet`，设置持久化存储时间为`1000秒`，并发送：

```java
@WebServlet("/cookieDemo4")
public class CookieDemo4 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //新建一个Cookie，并设置持久化存储时间为1000秒
        Cookie cookie = new Cookie("msg","hello");
        //设置持久化存储时间
        cookie.setMaxAge(1000);
        //发送cookie
        response.addCookie(cookie);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
```
运行服务器，首先访问`/cookieDemo4`，然后访问`/cookieDemo2`（这个是打印`cookie`信息到控制台的），浏览器关闭，再次打开`cookieDemo2`，也能获取到`cookie`：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210115161846869.png)
两次访问结果

# 三、cookie 能不能存中文
答：在`tomcat 8`之前，`cookie`中不能直接存储中文数据，需要将中文数据转码（一般采用`URL`编码）

**在`tomcat 8`之后，`cookie`支持中文数据**

读者可以自行实验
# 四、cookie 共享问题
这个问题详细一点说就是：假设在一个`tomcat`服务器中，部署了多个`web`项目，那么在这些`web`项目中的`cookie`能不能共享？

答：在默认情况下`cookie`不能共享

但是`cookie`有一个方法可以设置共享，那就是`setPath(String path)`，这个方法设置了`cookie`的获取范围，默认情况下，该值默认是当前项目的虚拟目录，如果要共享，只需要把`path`设置为`一根/`就好了。

演示一下：

新建一个`HttpServlet`，在这个类中发送一个`Cookie`，并且设置全局共享：

```java
@WebServlet("/cookieDemo5")
public class CookieDemo5 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //新建一个Cookie，并设置持久化存储时间为1000秒
        Cookie cookie = new Cookie("msg", "你好");
        //设置共享
        cookie.setPath("/");
        //发送cookie
        response.addCookie(cookie);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
```
再新建一个项目，把输出`cookie`信息的类拷贝过去，并且加上一句话：==第二个项目：==

下面运行服务器，先运行第一个发送`cookie`的项目，然后访问新建的项目，输出如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210115164210308.png)

