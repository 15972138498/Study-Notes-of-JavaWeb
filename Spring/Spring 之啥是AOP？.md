@[toc]

---
> 友情链接：
> [AOP是什么？](https://blog.csdn.net/lesileqin/article/details/113547039)
> [使用原生API接口实现AOP](https://blog.csdn.net/lesileqin/article/details/113554022)
> [使用自定义类实现AOP](https://blog.csdn.net/lesileqin/article/details/113555666)
> [使用注解实现AOP](https://blog.csdn.net/lesileqin/article/details/113557912)
# 一、AOP 简介
`AOP`全称（`Aspect Oriented Programming`）意味：面向切面编程，通过预编译方式和运行期动态代理实现程序功能的统一维护技术。

简单点说就是：**`AOP`是用来维护的，它的底层是动态代理模式，在开发中如果想要增加一些功能（比如日志），可以使用`AOP`在不改变原来业务逻辑代码的同时实现功能的增加。**

利用`AOP`可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，也提高了程序的可重用性，同时提高了开发效率。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210202114851876.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)

# 二、AOP 相关术语

- **横切关注点：** 跨越应用程序多个模块的方法或功能，就是与业务逻辑无关的，但是需要关注的部分，比如日志之类的
- **切面（`Aspect`）：** 横切关注点 被模块化 的特殊对象，就是一个类，也就是“增加的功能”的类
- **通知（`Advice`）：** 切面必须要完成的工作，也就是说，它是切面中的一个方法
- **目标（`Target`）：** 被通知的对象，
- **代理（`Proxy`）：** 向目标对象应用通知之后创建的对象，由`Spring`创建
- **切入点（`Point`）：** 切面通知执行的“地点”，就是方法在哪里执行
- **连接到（`JointPoint`）：** 与切入点匹配的执行点

看下图：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210202114804907.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
# 三、AOP 在 Spring 中的作用
在`Spring AOP`中，通过`Advice`定义横切逻辑，`Spring`支持5钟类型的`Advice`：

| 通知类型 | 连接点 | 实现接口 |
| -- | -- | -- |
| 前置通知 | 方法前 | `org.springframework.aop.MethodBeforeAdvice` |
| 后置通知 | 方法后 |  `org.springframework.aop.MethodAfterRunningAdvice` |
| 环绕通知 | 方法前后 | `org.aopalliance.intercept.MethodInterceptor` |
| 异常抛出通知 | 方法抛出异常 | `org.springframework.aop.ThrowsAdvice` |
| 引介通知 | 类中增加新的方法属性 | `org.springframework.aop.IntroductionInterceptor` |

    
