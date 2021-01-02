@[toc]


----
# 1、HTTP协议
[请点我了解`HTTP`数据格式](https://blog.csdn.net/lesileqin/article/details/112007609)



---

# 2、准备工作

在`IDEA`中新建一个模块，在`src`目录下，新建一个包，随后新建一个`Servlet`，并设置注解：

```java
@WebServlet("/requestDemo1")
public class RequestDemo1 extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
```

在`web`目录下新建一个`loginDemo1.html`，令这个表单映射到`requestDemo1`

```html
<form action="requestDemo1" method="post">
    <input type="text" name="username" placeholder="用户名"><br>
    <input type="password" name="userpwd" placeholder="密码"><br>
    <input type="checkbox" name="hobby" value="游戏">游戏
    <input type="checkbox" name="hobby" value="学习">学习
    <br>
    <input type="submit" name="btn">提交
</form>
```
最后设置`Tomcat`虚拟目录为：`/RequestDemo`

现在我们访问`http://localhost:8888/RequestDemo/loginDemo1.html`就可以得到如下界面：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210101135552430.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)


# 3、Request 对象获取请求行

现在我们的请求行的数据格式如下：
```java
GET /RequestDemo/loginDemo1.html HTTP/1.1
```
**方法如下表所示，标记:star:的是比较重要的方法，解释栏是对方法的解释，结果栏是根据上面的请求行得出的结果**

| 方法 | 解释 | 结果 |
| -- | -- | -- |
| `String getMethod()` | 获取请求方式| `GET` |
| :star:`String getContextPath()` | 获取虚拟目录 | `/RequestDemo` |
| `String getServletPath()` | 获取`Servlet`路径 | `/requestDemo1` |
| `String getQueryString()` | 获取`get`方式请求参数，`POST`为`null` |表单里的参数，以`url`的方式传送 |
| :star:`String getRequestURI()` | 获取`URI` | `/RequestDemo/requestDemo1` |
| :star:`String getRequestURL()` | 获取`URL` | `http://localhost:8888/RequestDemo/requestDemo1` |
| `String getProtocol()` | 获取协议及版本 | `HTTP/1.1` |
| `String getRemoteAddr()` | 获取客户机`IP`地址 | 返回访问该网站的`IP`地址 |

现在把上述的这些方法都实现一下：

```java
@WebServlet("/requestDemo1")
public class RequestDemo1 extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1、获取请求方式：String getMethod();
        String method = request.getMethod();
        System.out.println("请求方式：" + method);

        //2、（*）获取虚拟目录：String getContextPath();
        String contextPath = request.getContextPath();
        System.out.println("虚拟目录：" + contextPath);

        //3、获取Servlet路径：String getServletPath();
        String servletPath = request.getServletPath();
        System.out.println("Servlet路径：" + servletPath);

        //4、获取get方式请求参数，例如：name=xxx
        //   String getQueryString();
        String queryString = request.getQueryString();
        System.out.println("get方式获取参数：" + queryString);

        /*
         * 5、(*)获取请求URI和URL：
         *       String getRequestURI();
         *       String getRequestURL();
         * */
        String requestURI = request.getRequestURI();
        StringBuffer requestURL = request.getRequestURL();
        System.out.println("URI：" + requestURI);
        System.out.println("URL：" + requestURL);

        //6、获取协议及版本：String getProtocol();
        String protocol = request.getProtocol();
        System.out.println("协议：" + protocol);

        //7、获取客户机IP地址：String getRemoteAddr();
        String remoteAddr = request.getRemoteAddr();
        System.out.println("客户机IP地址：" + remoteAddr);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
```

然后运行该项目，输入表单以后，会得到以下结果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210102141102835.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70#pic_center)
在上面的代码`doGet`方法中，只是把`request`和`response`传入到了`doPost`方法中，这样做以后无论是哪种方式提交数据，代码都可以只写一遍，读者可以试一下。


---
# 4、Request 对象获取请求头
获取`请求头`的方法主要有两个，分别是：

- :star:`String getHeader(String name)`：通过请求头的额名称获取请求头的值
- `Enumeration<E> getHeaderNames()`：获取所有请求头的名称

其中`Enumeration<E>`是枚举类型，通过该类型的方法可以枚举对象集合中的元素

新建一个`Servlet`，并让`loginDemo.html`的表单映射到新建的`Servlet`。接下来分别实现上面的两个方法：

```java
@WebServlet("/requestDemo2")
public class RequestDemo2 extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            String value = request.getHeader(header);
            System.out.println(header + ": " + value);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
```
可以得到下面的结果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210102143036869.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70#pic_center)

使用`getHeader`可以帮助我们了解客户端是用哪个浏览器访问该资源的：

```java
String agent = request.getHeader("user-agent");
if(agent.contains("Chrome")){
	System.out.println("谷歌来啦！");
}else if(agent.contains("Firefox")){
	System.out.println("火狐来啦！");
}
```
现在分别用谷歌和火狐访问该资源，控制台就会输出：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210102144206276.png)

---

# 5、Request 对象获取请求体
需要说明的是只有`POST`才有请求体，在请求体中封装了`POST`的请求参数，在`Request`中把请求体封装成了`流`

获取请求体的步骤：

- a、获取流对象
- b、再从流对象中拿数据

**获取流对象的方法:**

- 字符流：`BufferedReader getReader()`，只能操作字符数据
- 字节流：`ServletInputStream getInputStream()`，可以操作所有类型数据

下面演示如何获取字符流，新建一个`Servlet`，并让`loginDemo.html`的表单映射到新建的`Servlet`：

```java
@WebServlet("/requestDemo3")
public class RequestDemo3 extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1、获取流对象
        BufferedReader reader = request.getReader();
        //2、从流中读取数据
        String line = null;
        while((line = reader.readLine()) !=null ){
            System.out.println(line);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
```

操作结果：
![在这里插入图片描述](https://img-blog.csdnimg.cn/2021010215094166.png)






