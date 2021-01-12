@[toc]

---
# 1、请求转发

请求转发是一种在`服务器内部`的资源跳转方式

步骤：

1. 通过`request`对象获取请求转发器对象：
    ```java
    RequestDispatcher getRequestDispatcher(String path); 
    ```
2. 使用`RequestDispatcher`对象来进行转发：
    ```java
    forward(ServletRequest request,ServletResponse response);
    ```
一般采用链式编程直接写成一行：

```java
request.getRequestDispatcher(String path).forward(request,response);
```

特点：

- 请求转发浏览器地址栏路径不发生变化
- 只能转发到当前服务器内部资源中
- 转发是一次请求

# 2、共享资源

域对象：一个有作用范围的对象，可以在范围内共享数据

`request`域：代表一次请求的范围，一般用于请求转发的多个资源中共享数据

方法：

- `void setAttribute(String name,Object obj)`：存储数据
- `Object getAttribute(String name)`：通过键获取值
- `void romoveAttribute(String name)`：通过键移除键值对
