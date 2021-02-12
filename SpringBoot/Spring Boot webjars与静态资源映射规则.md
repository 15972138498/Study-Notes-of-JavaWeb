@[toc]


-----
使用`Sping Boot`创建`web`应用时，避免不了要去找一些静态资源，所以静态资源放在哪儿，该怎么请求就是个问题了，`Spring Boot`帮我们做了这么几件事情：
# 一、webjars
`webjars`就是以`jar`包的方式引入静态资源

所有的`/webjars/**`，都去`classpath:META-INF/resources/webjars/`找资源

有个网站是：[https://www.webjars.org/](https://www.webjars.org/)，在这里可以下载静态资源的一些`jar`包，然后使用`maven`导入依赖

在这个网站上找到`jquery`：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210212112058425.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
复制`maven`依赖到项目的`pom.xml`：
```xml
<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>jquery</artifactId>
    <version>3.5.1</version>
</dependency>
```
导入之后，打开`idea`点击左侧`External Libraries`找到`webjars`：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210212112405257.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
启动服务器，在浏览器输入：
```
http://localhost:8080/webjars/jquery/3.5.1/jquery.js
```
就可以请求到该资源：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210212112527525.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)

# 二、"/**" 访问当前项目的任何资源
`/**`访问当前项目的任何资源，主要是指静态资源的文件夹
```
"classpath:/META-INF/resources/",
"classpath:/resources/",
"classpath:/static/",
"classpath:/public/"

"/"：当前项目的根路径
```

也就是说，我们可以把静态资源放在这几个包下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210212113503955.png)
现在往`static`目录下放几个资源：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210212113624307.png)


如果要访问资源只需要请求：
```
http://localhost:8080/asserts/js/Chart.min.js
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210212113727739.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
就可以了
# 三、欢迎页
静态资源文件夹下的所有`index.html`页面，会被`/**`映射

新建一个`index.html`放在`static`目录下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210212114002554.png)
访问：
```xml
http://localhost:8080/
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210212114022352.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
