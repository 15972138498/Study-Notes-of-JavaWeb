@[toc]


----

# 一、简介
**`Spring`是一个基于控制反转`Ioc`和面向切面编程`Aop`的轻量级开源框架！**

它是由一个叫做`Rod Johnson`的**音乐学博士**在2002年提出并创建的，他提出了著名的`轮子理论`，就是：**不要重复发明轮子**

`Spring`之所以叫做`Spring`，就是它期望给软件行业带来一个春天！让我们的开发变得更加简单更加快速。它使得现有的技术变的更加容易使用，它本身是一个大杂烩，整合了现有的技术框架

`Spring`有如下优点：

- 低侵入式设计，代码污染极低
- 独立于各种应用服务器，基于`Spring`框架的应用，可以真正实现`Write Once,Run Anywhere`的承诺
- `Spring`的`DI`机制降低了业务对象替换的复杂性，提高了组件之间的解耦
- `Spring`的`AOP`支持允许将一些通用任务如安全、事务、日志等进行集中式管理，从而提供了更好的复用
- `Spring`的`ORM`和`DAO`提供了与第三方持久层框架的良好整合，并简化了底层的数据库访问
- `Spring`并不强制应用完全依赖于`Spring`，开发者可自由选用`Spring`框架的部分或全部



# 二、获得 Spring
第一钟获取方式可以直接到`GitHub`：

[https://github.com/spring-projects/spring-framework](https://github.com/spring-projects/spring-framework)

点击`Releases`，然后下载即可：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210129200657688.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
超下滑：
![在这里插入图片描述](https://img-blog.csdnimg.cn/2021012920070735.png)

第二种获取方式，可以使用`Maven`，建议注入`springframework`依赖：
```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.2.12.RELEASE</version>
    <scope>test</scope>
</dependency>
```
这样可以导入其他的包，很方便：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210129200838461.png)

# 三、Spring 的组成部分
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210129200855837.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)


