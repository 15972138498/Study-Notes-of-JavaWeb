`SQL`注入是什么？

看一下百度百科的定义：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210109141412262.png)
啊，好长一大段文字，些许不想看，下面通过一个例子，来说明一下什么是`SQL注入`：

新建一个数据库，再建一个表，添加两行数据：

```sql
use db1;
create table user(
	id int primary key auto_increment,
	username varchar(32),
	password varchar(32)
);

insert into user values(null,'zhangsan','123');
insert into user values(null,'lisi','234');
```
表如下图所示：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210109141600967.png)
再随随便便用`JDBC`写个登陆操作：

```java
package com.wzq.jdbc;

import com.wzq.util.JDBCUtils;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
/*
 *   需求：
 *       1、通过键盘录入用户名和密码
 *       2、判断用户是否登陆成功
 * */
public class JDBCDemo05 {

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String username = cin.nextLine();
        System.out.println("请输入密码：");
        String password = cin.nextLine();

        boolean res = new JDBCDemo05().login(username, password);
        if (res) System.out.println("登陆成功！");
        else System.out.println("登陆失败！");

    }

    public boolean login(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            //1、获取数据库连接
            conn = JDBCUtils.getConnection();   //JDBCUtils工具类
            //2、定义sql
            String sql = "select * from user where username = '" + username + "' and password = '" + password + "'";
            //3、获取执行sql的对象
            stmt = conn.createStatement();
            //4、执行sql
            rs = stmt.executeQuery(sql);
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, stmt, conn);
        }
        return false;
    }
}
```
测试一下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210109142641759.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
可以看到，普通的检验没有任何问题，现在使用`SQL注入`：

账户名称随便输入，密码输入：`a' or 'a'='a`
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210109142816533.png)
惊讶的发现，居然登陆成功了。输出一下`sql`看一下：

```sql
select * from user where username = 'askjdhjksahd' and password = 'a' or 'a' = 'a'
```
可以看到`where`之后的条件，无论是什么结果都为真，都会输出整个表：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210109143319872.png)
所以，综上所述：在`sql`拼接时，有一些`sql`的特殊关键字参与字符串的拼接，就会造成安全性问题，这就是上面为什么能登陆成功的原因所在。

---

那怎么解决这个问题呢？

答：利用`PreparedStatement`对象，不使用`Statement`对象。

`PreparedStatement`对象是`Statement`对象的子类，它是预编译的`sql`，所以运行速度会比`Statemnet`更快。

`PerpaerdStatement`使用`?`作为占位符，使用`setXxx(索引,值)`给`?`赋值

所以我们替换一下`Statement`，写一下代码：

```sql
    public boolean login(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            //1、获取数据库连接
            conn = JDBCUtils.getConnection();   //JDBCUtils类
            //2、定义sql
            String sql = "select * from user where username = ? and password = ?";
            //3、获取执行sql的对象
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,username);
            pstmt.setString(2,password);
            //4、执行sql
            rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, pstmt, conn);
        }
        return false;
    }
```
测试一下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210109145248680.png)
成功解决！
