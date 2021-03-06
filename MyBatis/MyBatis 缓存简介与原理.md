@[toc]

----

> [MyBatis 缓存及原理](https://blog.csdn.net/lesileqin/article/details/113348677)
> [MyBatis 一级缓存](https://blog.csdn.net/lesileqin/article/details/113352616)
> [MyBatis 二级缓存](https://blog.csdn.net/lesileqin/article/details/113356160)

# 一、缓存简介
**什么是缓存？**

- 缓存就是存在**内存**中的临时数据
- 将用户**经常查询**的数据放在缓存中，用户去查询数据就不需要再次访问数据库或者硬盘，这样就可以起到加速查询的作用，同时也减轻了数据库或硬盘的开销，从而达到提高查询效率，解决高并发的问题

**什么样的数据能使用缓存？**

答：用户**经常查询**且**不经常改变**的数据

# 二、MyBatis 缓存

`MyBatis`包含了一个非常强大的查询缓存特性，它可以非常方便的定制和配置缓存。

`MyBatis`在系统中默认定义了两级缓存：**一级缓存**和**二级缓存**

- 在默认的情况下，只有一级缓存开启，它是`SqlSession`级别的缓存，它的生命周期从`SqlSession`启动到关闭，平常`一级缓存`也成为`本地缓存`
- 二级缓存需要手动的开启和配置，它是基于`Mapper`级别的缓存，也就是说有一个`***Mapper.xml`文件就可以开启一个二级缓存
- `MyBatis`为了提高扩展性，定义了缓存接口`Cache`，可以通过实现`Cache`接口来自定义二级缓存

# 三、MyBatis 缓存原理
如下图所示：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210128170120431.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
用户访问数据库顺序：

- 首先看二级缓存中有没有
- 再看一级缓存中有没有
- 如果都没有查询数据库
- 当`sqlSession`关闭时，一级缓存自动提交到二级缓存
