@[toc]

---
# 一、会话技术
因为`Cookie`属于一种会话技术，所以在解释`Cookie`与`Session`之前，先来看一下什么是会话技术。

`会话`即`说话`，今天我在街上碰到了张三，我们两个是很多年没有见的老朋友，所以我们你一句我一句，直到我们两个其中一个走了，那么这一次说话结束。

那么`会话`也是这样的，浏览器和服务器之间可能会产生多次的请求和响应，直到浏览器和服务器一方关闭，这次`会话`就结束了。这样的过程称为：==一次会话==

那么，如何保存这一次会话中的数据信息，就是一个很大的问题。`Cookie`与`Session`就帮助这次会话保存了数据信息。

`Cookie`为客户端会话技术，数据信息保存在客户端（浏览器）

`Session`为服务器端会话技术，数据信息保存在服务器

# 二、Cookie

通过一个案例来学习`Cookie`的实现原理，定义两个`HttpServlet`：

第一个用来发送`Cookie`：

```java
@WebServlet("/cookieDemo1")
public class CookieDemo1 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 创建Cookie对象
        Cookie cookie = new Cookie("msg","hello");
        //2. 发送Cookie
        response.addCookie(cookie);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
```

第二个用来获取`Cookie`：

```java
@WebServlet("/cookieDemo2")
public class CookieDemo2 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //3. 获取Cookie，拿到数据
        Cookie[] cookies = request.getCookies();
        //如果cookies不为空，就拿到cookies里面所有的value
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
现在运行服务器，首先访问`cookieDemo1`，点击`F12`，选择网络，可以发现，第一次访问`cookieDemo1`时，服务器响应给浏览器了`cookie`信息
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210115143548453.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)

然后访问`cookieDemo2`，请求头携带`cookie`信息给服务器
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210115144111952.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)

可能上面的文字不太好理解，下面画张图就清楚明了了：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210115145041545.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)

第一次访问`cookieDemo1`，服务器给出响应并设置了响应头`set-cookie : msg=hello`，浏览器存储`cookie`信息；第二次访问`cookieDemo2`，浏览器把存储的信息封装到请求头钟发送给服务器。

# 三、Session

在开始`Session`之前，先说明一点：**`Session`是依赖于`Cookie`的**，暂且先搁置`为什么`，通过一个案例，先看一下：

新建两个`HttpServlet`：

第一个用于设置信息：

```java
@WebServlet("/sessionDemo1")
public class SessionDemo1 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 获取HttpSession对象
        HttpSession session = request.getSession();
        //2. 存储数据
        session.setAttribute("msg","hello");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
```
第二个用于获取`Session`信息：

```java
@WebServlet("/sessionDemo2")
public class SessionDemo2 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 获取HttpSession对象
        HttpSession session = request.getSession();
        //2. 获取数据
        Object msg = session.getAttribute("msg");
        System.out.println(msg);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
```
运行服务器，访问第一个，可以看到，第一次访问，响应头携带了一个`Cookie`信息，这个`cookie`信息，是：

==JSESSIONID=67680D84ACA2B7844FCCF3F4FE89E84B==

哎哟，这是个啥东西？不急不急，请继续往下看：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210115152107272.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
第二次访问，这次我们发现响应头也携带了这么一个`cookie`信息：

==JSESSIONID=67680D84ACA2B7844FCCF3F4FE89E84B==

**与上面那个一模一样！**
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210115152208808.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)

下面进入到大揭秘环节，和`Cookie`一样，先画张图：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210115153728989.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
因为`Session`对象的数据是保存在服务器端的并且只有一个，那么在一次会话中如何确保多次获取的`Session`是同一个就是一个很大的问题，为了解决这个问题，谁谁谁把`Session`对象设置了一个`id`，并通过`Cookie`把`id`发送给浏览器，这样浏览器只用通过`id`就能准备的找到`Session`对象在哪里了。所以说：**`Session`是依赖于`Cookie`的。**
