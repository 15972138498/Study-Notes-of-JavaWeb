@[toc]

----
# 一、MyBatis 简介
下面这种带”愤怒的小鸟“的图片就是`MyBatis`的LOGO：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210119213902616.png)
那什么是`MyBatis`呢？答：

- 它是一款优秀的**持久层框架**
- 它支持定制化`sql`、存储过程以及高级映射
- `MyBatis`几乎避免了几乎所有的`JDBC`代码和手动设置参数以及获取结果集
- `MyBatis`可以使用简单的`XML`或注解来配置和映射原生类型，接口和`Java`的`JavaBean`为数据库中的记录

它具有以下的优点：

- 相比于传统的`JDBC`，`MyBatis`更加的简单易学、自动化、灵活
- `sql`和代码分离，提高了项目的可维护性
- 提供映射标签，支持对象与数据库的`orm`字段关系映射
- 提供`xml`标签，支持编写动态`sql`
- 提供对象关系映射标签，支持对象关系组建维护

# 二、什么是持久层
在解释什么是**持久层**之前，先说一下**持久化**

通俗一点来说，==持久化就是把很快就会没有的东西保存起来让他更持久一点==，专业点说就是：**将程序数据在持久状态和瞬时状态转化的过程**。内存有一个特点就是`断电即失`，但是有一些在内存中的对象，不能让他丢掉，所以说持久化要做就是不让它丢掉。**持久层就是完成持久化工作的代码块**。
# 三、如何获得 MyBatis
## 1、Maven
可以直接将下面的代码复制的`pom.xml`文件中的`<dependencies>`标签下：

```xml
<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.5.6</version>
</dependency>
```

## 2、GitHub
[可以直接点这些字跳转到`MyBatis`在`GitHub`的仓库](https://github.com/mybatis/mybatis-3)

然后点击：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210119215550857.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
这个就可以下载`MyBatis`的任意版本


