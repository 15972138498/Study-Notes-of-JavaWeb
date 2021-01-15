@[toc]

-----

# 一、当客户端关闭后，服务器不关闭，两次获取Session是否为同一个？

答：在默认情况下，不是同一个。如果需要两个`Session`相同，则可以创建一个`Cookie`对象，键位`JSESSIONID`，设置一下最大存活时间，让`Cookie`持久化保存`Session`的`ID`，就可以实现客户端关闭，两次获取`Session`是同一个了。

下面演示一下：

```java
@WebServlet("/sessionDemo3")
public class SessionDemo3 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取HttpSession对象
        HttpSession session = request.getSession();
        System.out.println(session);

        //期望客户端关闭之后，session也能相同
        Cookie cookie = new Cookie("JSESSIONID",session.getId());
        cookie.setMaxAge(60 * 10); //设置cookie存活时间为十分钟
        response.addCookie(cookie); //发送cookie
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
```
运行服务器，打开浏览器访问该`Servlet`之后，关闭浏览器，再次访问，控制台输出结果如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210115213210318.png)
# 二、客户端不关闭，服务器关闭后，两次获取的Session是同一个吗？
答：不是同一个，但是为了确保数据不丢失，`tomcat`自动完成了下面这两个工作：

- `session的钝化`：在服务器正常关闭之前，将`session`对象序列化到硬盘上
- `session的活化`：在服务器启动后，将`session`文件转化为内存中的`session`对象

这样就完成了数据的不丢失，**`tomcat`能**帮助我们完成上面的工作，但是，**`IDEA`不能！**

至于为什么`Tomcat`可以完成这个工作呢？我们新写两个`Servlet`，一个用于`session`存储数据，一个用于获取`session`数据并打印（代码简单，不再粘贴）

然后把该项目在`out`文件夹下生成的项目压缩一下，打包为以`.war`为后缀名的文件，然后把他拷贝在`Tomcat`目录下的`webapps`目录下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/2021011521413693.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
运行`Tomcat`，打开一个浏览器，访问发送`session`的`Servlet`，然后访问接收`session`的`Servlet`，可以看到控制台打印出了`hello`：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210115214414276.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
OK，现在我们让`Tomcat`正常关闭（`shutdown.bat`），关闭之后，`Tomcat`开始执行`钝化`操作。

它把`Session`序列化到了：==apache-tomcat-8.5.61\work\Catalina\localhost\SessionDemo==文件夹下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210115214607936.png)
重启`Tomcat`，即执行`活化`操作，上面的这个保存序列号的文件也会被自动删除，紧接着，我们访问接收信息的`Servlet`：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210115214817160.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
控制台输出了`hello`！成功！
# 三、Session什么时候被销毁？
`Session`在服务器关闭后被销毁，如果打开一个页面，在一定时间内不动电脑，`Session`也会被销毁

如果我们期望`session`可以存活时间久一点，我们可以到`tomcat\conf\web.xml`文件下，找到：

```xml
  <!-- ==================== Default Session Configuration ================= -->
  <!-- You can set the default session timeout (in minutes) for all newly   -->
  <!-- created sessions by modifying the value below.                       -->

    <session-config>
        <session-timeout>30</session-timeout>
    </session-config>
```
这段代码，更改那个`30`就好了，这个30就是默认的30分钟。
