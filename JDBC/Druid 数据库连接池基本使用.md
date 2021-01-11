@[toc]

---
# 一、什么是数据库连接池？

假设张三开了个餐馆，每天都会有很多人来这个餐馆吃饭，这时候就需要大量的服务员去服务。但是张三好像不太聪明的样子，来一桌客人他就雇一个服务员，当这桌客人吃完饭走了之后，张三就立即辞退了刚顾的服务员，每桌的服务员都是这样的待遇…………

OK，我们现在翻译一下，上面的话：

**使用`JDBC`时候，我们每次操作数据库，都会新建一个数据库连接对象，操作完成之后，就会释放这个数据库连接对象，这就像极了上面的 “雇了一个服务员之后，就立即辞退他” ……**

这样搞来搞去，服务员也有情绪，而且张三的饭店也经不起天天去雇人（**频繁创建和销毁连接所带来的巨大的开销；在高并发的情况下，要同时创建很多数据库连接，可能会导致服务器崩溃！**）。

那么我们为什么不可以顾久一点服务员呢？哦，原来服务员没有地方住，好，给他盖`房子`！

这个`房子`就是数据库连接池，如下图所示：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210110152840320.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)

把所有的连接对象都放在了池子里面，用的时候拿就完事儿了！

# 二、Druid 简介
`Druid`连接池是阿里巴巴开源的数据库连接池项目，它被誉为`Java语言中最好的数据库连接池`，如下表所示：

| 功能 | `dbcp` | `Druid` | `c3p0` | `tomact-jdbc` | `HikariCP` |
| --      | --         | --         | --         |--                     | --              |
| 是否支持`PSCache` | 是 |  是 | 是 | 否 | 否 |
| 监控 | `hmx` |  `jmx/log/http` | `jmx/log` | `jmx` | `jmx` |
| 扩展性 | 弱 |  好  | 弱 | 弱 | 弱 |
| `sql`拦截及解析 | 无 |  支持 | 无 | 无 | 无 |

可以看到`Druid`相比于其他数据库连接池技术有比较好的优势。

# 三、Druid 使用基本步骤
新建项目，在项目下新建`libs`目录

1、 复制`jar`包：`druid-1.0.9.jar`和`mysql-connector-java-8.0.15.jar`到`libs`目录下：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210110154617341.png)

选中这两个`jar包`，点击右键，选中`add as Library`，然后点击OK，即导入成功

2、定义配置文件，在`Druid`中，配置文件是`properties`形式的，它可以叫任意名称，可以放在任意目录下。

在`src`目录下，新建文件，名字为`druid.properties`：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210110155156943.png)

打开该文件，按照下面的格式写入：

```sql
driverClassName=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://localhost:3306/db1?serverTimezone=GMT
# 数据库名
username=root
# 数据库密码
password=root
# 初始化多少个连接对象
initialSize=5
# 最大连接对象个数
maxActive=10
# 最长等待时间
maxWait=3000
```
3、新建一个类，新建`main`方法，开始写代码，**首先加载配置文件**：

```java
Properties pro = new Properties();
InputStream id = DruidDemo.class.getClassLoader().getResourceAsStream("druid.properties");
pro.load(id);
```
4、通过工厂函数`DruidDataSourceFactory`来获取数据库连接池对象

```java
DataSource ds = DruidDataSourceFactory.createDataSource(pro);
```
5、获取连接：

```java
Connection conn = ds.getConnection();
```


完成！

完整代码如下：

```java
package com.wzq.dataSource.druid;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class DruidDemo {
    public static void main(String[] args) throws Exception {
        //3.加载配置文件
        Properties pro = new Properties();
        InputStream id = DruidDemo.class.getClassLoader().getResourceAsStream("druid.properties");
        pro.load(id);
        //4.获取连接池对象
        DataSource ds = DruidDataSourceFactory.createDataSource(pro);
        //5.获取连接
        Connection conn = ds.getConnection();
        System.out.println(conn);
    }
}

```

打印结果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210110160018176.png)

# 四、Druid 工具类

该工具类提供了：获取连接，释放资源，获取连接池的方法

直接给代码：
```java
package com.wzq.dataSource.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtils {

    //定义成员变量
    private static DataSource ds;

    static {
        try {
            //1.加载配置文件
            Properties pro = new Properties();
            pro.load(JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties"));
            //2.获取dataSource
            ds = DruidDataSourceFactory.createDataSource(pro);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取连接
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    //释放资源
    public static void close(Statement stmt,Connection conn){
        close(null,stmt,conn);
    }

    public static void close(ResultSet rs, Statement stmt,Connection conn){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(stmt!=null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(conn!=null){
            try {
                conn.close(); //归还连接
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //获取连接池
    public static DataSource getDataSource(){
        return ds;
    }

}

```
写一个类测试一下：

```java
public class DruidDemo2 {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            //1.获取连接对象
            conn = JDBCUtils.getConnection();
            //2.定义sql
            String sql = "insert into account values(null,?,?)";
            //3.获取pstmt对象
            pstmt = conn.prepareStatement(sql);
            //4.给?赋值
            pstmt.setString(1,"wangwu");
            pstmt.setDouble(2,3000);
            //5.执行
            int count = pstmt.executeUpdate();
            System.out.println(count);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //6.释放资源
            JDBCUtils.close(pstmt,conn);
        }
    }
}
```
添加成功：

![在这里插入图片描述](https://img-blog.csdnimg.cn/2021011016455235.png)

