@[toc]

---
# 一、从官网下载
百度搜索`Spring`：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210211215816964.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
进入官网之后，鼠标移到`Project`，然后点击`Spring Boot`：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210211215915796.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/2021021122000671.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
上图是点击去的界面，朝下滑，滑倒最底下，有个：`Spring Initializr`：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210211220107592.png)
点进去：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210211220418172.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
然后添加依赖：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210211220457850.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
最后点击
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210211220544973.png)
即可下载压缩包，然后解压缩：
![在这里插入图片描述](https://img-blog.csdnimg.cn/2021021122064155.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
这就是一个什么都没有`Spring Boot`程序，随后可以导入到`idea`写代码
# 二、使用idea创建
每次都从官网下载太麻烦了，好在`idea`集成了`Spring Boot`，可以直接从`idea`创建：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210211221130877.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
点击下一步：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210211221632594.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
下一步：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210211221744795.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
然后点击`Finish`就创建成功啦！

打开`pom.xml`，可以看到`idea`帮我们导入了好多的包与插件：
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-tomcat</artifactId>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```
