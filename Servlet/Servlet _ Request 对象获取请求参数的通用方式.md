@[toc]

---
# -1、解决中文乱码问题
在开始之前，先解决中文乱码的问题。在`Tomcat 8`以上已经解决了`GET`方法中文乱码问题，但是`POST`方式的乱码没有解决，我们只需要在代码中加一行：

```java
request.setCharacterEncoding("utf-8");
```
就可以了

---

# 0、概述

进入正题，下表展示了获取请求参数通用方式的常用方法：

| 方法 | 说明 |
| -- | -- |
| :star:`String getParameter(String name)` | 根据参数名称获取参数值 |
| :star:`String[] getParameterValues(String name)` | 根据参数名称获取参数值的数组 |
| :star:`Enumeration<String> getParameterNames()` | 获取所有请求参数的名称 |
| :star:`Map<String,String[]> getParameterMap()` | 获取所有参数的`Map`集合 |


在演示这四个方法之前，新建一个`Servlet`，再新建一个表单，让他映射到刚刚创建的`Servlet`

```html
<form action="requestDemo4" method="post">
    <input type="text" name="username" placeholder="用户名">
    <br>
    <input type="password" name="password" placeholder="密码">
    <br>
    <input type="checkbox" name="hobby" value="游戏">游戏
    <input type="checkbox" name="hobby" value="学习">学习
    <br>
    <input type="submit" value="提交">
</form>
```
接下来演示这四个方法：

---

# 1、getParameter 根据参数名称获取参数值
如下图所示：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210102162624614.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
其中红色框子是参数名称，蓝色框子我们输入的是值

现在用`getParameter`来获取一下所有用户输入的值，代码如下：

```java
@WebServlet("/requestDemo4")
public class RequestDemo4 extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //0、解决中文乱码问题
        request.setCharacterEncoding("utf-8");
        
        System.out.println("1、根据参数名称获取参数值：");
        //1、根据参数名称获取参数值
        String username = request.getParameter("username");
        System.out.println("username : " + username);
        String password = request.getParameter("password");
        System.out.println("password : " + password);
        String hobby1 = request.getParameter("hobby");
        System.out.println("hobby : " + hobby1);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
```
填写表单，复选框全选：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210102163457307.png)
点击提交之后，运行结果如下：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210102163521553.png)
可以发现，我们两个复选框的都选中了，但是用`getParameter`的方式只获取了第一个复选框的值，显然是不符合我们要求的


---
# 2、getParameterValues 根据参数名称获取参数值的数组

为了解决上面的那个问题，引入了`getParameterValues`这个方法，这个方法返回的是参数值数组

根据上面的案例，只编写针对复选框的代码：

```java
@WebServlet("/requestDemo4")
public class RequestDemo4 extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //0、解决中文乱码问题
        request.setCharacterEncoding("utf-8");

        System.out.println("2、根据参数名称获取参数值的数组：");
        //2、根据参数名称获取参数值的数组
        String[] hobbies = request.getParameterValues("hobby");
        for (String hobby : hobbies) {
            System.out.println("hobby : " + hobby);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
```
得到的结果是：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210102163959101.png)
成功解决！


---
# 3、getParameterNames 获取所有请求参数的名称

`getParameterNames`方法返回的是`Enumeration`类型，下面实现一下：

```java
@WebServlet("/requestDemo4")
public class RequestDemo4 extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //0、解决中文乱码问题
        request.setCharacterEncoding("utf-8");

        //3、获取所有请求参数的名称
        System.out.println("3、获取所有请求参数的名称");
        Enumeration<String> parameterNames = request.getParameterNames();
        while(parameterNames.hasMoreElements()){
            String name = parameterNames.nextElement();
            String value = request.getParameter(name);
            System.out.println(name + " : " + value);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
```
复选框全选，实现效果：
![在这里插入图片描述](https://img-blog.csdnimg.cn/2021010217343993.png)
这个方法的好处是，不需要知道参数名称，就可以直接枚举出所有的参数名称和参数值，如果参数太多，用上面的通过参数名称获取值代码会变得很多很冗余；但是它有一个致命的缺点就是：它只能获取复选框选中的第一个值…………………………

请继续往下看第四个方法：


----
# 4、getParameterMap 获取所有参数的Map集合

第四种方法完美解决了上面的问题，它返回`Map<String,String[]>`，所以只需要遍历`Map`就好了，实现一下：

```java
@WebServlet("/requestDemo4")
public class RequestDemo4 extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //0、解决中文乱码问题
        request.setCharacterEncoding("utf-8");

        System.out.println("-----------------------------------------------------");
        //4、获取所有参数的Map集合
        System.out.println("4、获取所有参数的Map集合");
        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<String> keySet = parameterMap.keySet();
        for(String name : keySet){
            String[] strings = parameterMap.get(name);
            System.out.print(name + ": ");
            for (String value : strings){
                System.out.print(value + " ");
            }
            System.out.println();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
```
复选框全选，结果如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210102173812769.png)
`Bingo！！！！`
