@[toc]


---
# 一、JdbcTemplate 简介
`JdbcTemplate`是`Spring`框架对`JDBC`的简单封装，它提供了一个`JdbcTemplate`对象**简化**了`JDBC`的开发。

所以：**懒是第一生产力！**
# 二、使用JdbcTemplate的步骤
1、导入`jar`包：

- `mysql-connector-java-8.0.15.jar`
- `spring-beans-5.0.20.RELEASE.jar`
- `spring-core-5.0.20.RELEASE.jar`
- `spring-jcl-5.0.20.RELEASE.jar`
- `spring-jdbc-5.0.20.RELEASE.jar`
- `spring-tx-5.0.20.RELEASE.jar`

下载地址：[https://jar-download.com/?search_box=jdbcTemplate](https://jar-download.com/?search_box=jdbcTemplate)

2、创建`JdbcTemplate`对象，依赖于数据源`DataSource`

```java
JdbcTemplate template = new JdbcTemplate(ds);
```

`JdbcTemplate`依赖于了`数据库连接池`，如果没有这方面的知识储备，[请点击这些字先行学习`Druid数据库连接池的基本操作`](https://blog.csdn.net/lesileqin/article/details/112428359)

3、调用`JdbcTemplate`的方法来完成`CRUD`（增删改查）的操作，主要方法如下表所示：

| 方法 | 说明 |
| -- | -- |
| `execute` | 用于执行任何`SQL`语句 | 
| `update` | 执行`DML`语句，增、删、改语句 |
| `query` | 查询结果，将结果封装为`JavaBean`对象 |
| `queryForXxx` | 查询结果，将结果封装为`Xxx`对象 |

# 三、JdbcTemplate的简单实现

按照上面的步骤，新建一个模块，再新建一个文件夹`libs`，将五个`jar包`复制进去
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210111142741967.png)
选中五个包，点击右键，选择`add as Library`，然后点击`OK`，即导包成功！

[新建一个`Druid工具类`](https://blog.csdn.net/lesileqin/article/details/112428359)，然后再新建一个类，创建`JdbcTemplate`对象，把`DataSource`对象传进去。

```java
JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
```

最后一步，定义`sql`语句，然后调用执行`sql`的方法：

```java
String sql = "update account set balance = 5000 where id = ?";
int cnt = template.update(sql, 3);
System.out.println(cnt);
```
完整代码：

```java
package com.wzq.jdbctemplate;

import com.wzq.utils.JDBCUtils;
import org.springframework.jdbc.core.JdbcTemplate;
//jdbcTemplate简单演示
public class JdbcTemplateDemo01 {
    public static void main(String[] args) {
        //2.创建JdbcTemplate对象
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
        //3.调用方法
        String sql = "update account set balance = 5000 where id = ?";
        int cnt = template.update(sql, 3);
        System.out.println(cnt);
    }
}

```
控制台输出结果：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210111143254874.png)
查询一下数据库：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210111143338444.png)
`id`为`3`的`balance`字段确实已被更新。`JdbcTemplate`演示成功！

对比以前的`JDBC`是不是少了很多代码，确实大大的简化了开发。

---------

[JdbcTemplate 执行 DML 增删改操作](https://blog.csdn.net/lesileqin/article/details/112470234)

[JdbcTemplate 执行 DQL 查询操作](https://blog.csdn.net/lesileqin/article/details/112475947)

