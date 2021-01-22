@[toc]

-----

# 1、STDOUT_LOGGING
`STDOUT_LOGGING`是`标准日志输出`，他写在`mybatis-config.xml`文件的`<settings>`标签内：

```xml
    <settings>
        <setting name="logImpl" value="STDOUT_LOGGING"/>
    </settings>
```
他不需要再注入其他依赖，直接就能跑起来，现在写一个测试类，看一下他的输出：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210122222718324.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
下面看另外一种`日志`
# 2、LOG4J
- `LOG4J`是`Apache`的一个开源项目，通过使用`LOG4J`，我们可以控制日志信息输送的目的地是控制台、文件等
- 我们可以控制每一条日志的输出格式
- 通过定义每一条日志信息的级别，我们能够更加细致的控制日志的生成过程
- 通过一个**配置文件**来灵活的进行配置，而不需要修改应用的代码

和`STDOUT_LOGGING`一样，使用`LOG4J`需要事先设置`MyBatis-config.xml`中的`<settings>`：

```xml
    <settings>
        <setting name="logImpl" value="LOG4J"/>
    </settings>
```
接下来我们需要加入一个配置文件，名字为：`log4j.properties`，需要加入以下内容（不用记住！需要用的时候百度就好了）：

```sql
#将等级为DEBUG的日志信息输出到console和file这两个目的地，console和file的定义在下面的代码
log4j.rootLogger=DEBUG,console,file

#控制台输出的相关设置
log4j.appender.console = org.apache.log4j.ConsoleAppender
log4j.appender.console.Target = System.out
log4j.appender.console.Threshold=DEBUG
log4j.appender.console.layout = org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=[%c]-%m%n

#文件输出的相关设置
log4j.appender.file = org.apache.log4j.RollingFileAppender
log4j.appender.file.File=./log/wzq.log
log4j.appender.file.MaxFileSize=10mb
log4j.appender.file.Threshold=DEBUG
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern= [%p][%d{yy-MM-dd}][%c]%m%n

#日志输出级别
log4j.logger.org.mybatis=DEBUG
log4j.logger.java.sql=DEBUG
log4j.logger.java.sql.Statement=DEBUG
log4j.logger.java.sql.ResultSet=DEBUG
log4j.logger.java.sql.PreparedStatement=DEBUG
```
然后打开测试类输出一下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210122223414894.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
同时他会输出日志文件：
![在这里插入图片描述](https://img-blog.csdnimg.cn/2021012222344974.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
他也可以在`java`代码中向日志输出信息，分为三个等级：`info`、`debug`、`error`：

```java
    @Test
    public void log4jTest(){
        Logger logger = Logger.getLogger(UserMapperTest.class);
        logger.info("info：进入log4jTest了...");
        logger.debug("debug：进入log4jTest了...");
        logger.error("error：进入log4jTest了...");
    }
```
运行一下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210122223609885.png)
打印出来了，再来看看日志文件：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210122223643462.png)
在最前面会输出是哪种等级的日志信息
