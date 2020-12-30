
@[toc]


-----

# Servlet 体系结构

`Servlet`是一个接口，在`Java EE API`中，我们可以看到有一个抽象类`GenericServlet`实现了`Servlet`接口，在抽象类`GenericServlet`又有一个抽象类`HttpServlet`类继承了它。

也就是说：`Servlet`接口是爷爷，`GenericServlet`抽象类是爸爸，`HttpServlet`抽象类是孙子。画个图看一下：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201230203832813.png#pic_center)

接下来分别看一下三个类

## 1、Servlet

[关于这一部分，请点我快速了解！](https://blog.csdn.net/lesileqin/article/details/111992194)

## 2、GenericServlet

`GenericServlet`帮我们把`Servlet`接口中的`init`、`getServetConfig`、`getServetInfo`、`destory`这四个方法做了空实现，只留了一个抽象类`service`。继承这个类，可以使代码更简洁，开发也会更高效。

```java
@WebServlet("/demo3")
public class ServletDemo3 extends GenericServlet {
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

    }
}
```
但是实际开发中，用的却是`HttpServlet`
## 3、HttpServlet

在`service`方法中，一般都要获取从浏览器发来的数据，这时候我们就要判断`http`用的是哪种请求方式了。

下面是传统判断请求方式的代码：

```java
String method = req.getMethod();
	//get方式获取数据
	if("GET".equals(method)){
		//执行相应操作
	}
	//POST方式获取数据
	else if("POST".equals(method)){
	//执行相应操作
    }
```
这种方式要对各种请求方式进行判断，每个`Servlet`都要判断到底。显然，这在开发过程中会很麻烦，为了解决这个问题，`HttpServlet`对这些个判断，进行了封装，形成了下面的这些个方法：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201230211224499.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70#pic_center)
操作步骤如下：

第一步、定义一个类继承`HttpServlet`

```java
@WebServlet("/demo4")
public class ServletDemo4 extends HttpServlet{

}
```
第二步、在`web`目录下新建一个`test.html`文件

```html
<form action="demo4" method="post">
    <input name="txt">
    <input type="submit" value="提交">
</form>
```
第三步、回到`ServletDemo4`，重写`doPost()`方法

```java
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doPost is run......");
    }
```
至此完成，打开浏览器输入`localhost:8080/text.html`即可出现：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201230232403962.png#pic_center)


