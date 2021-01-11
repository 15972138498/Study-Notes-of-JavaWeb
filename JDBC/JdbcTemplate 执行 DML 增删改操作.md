@[toc]


----
# 〇、写在前面
`JdbcTemplate`依赖于数据库连接池，如果没有相应的知识储备，[请点这些文字先行学习`Druid数据库连接池`](https://blog.csdn.net/lesileqin/article/details/112428359)

`JdbcTemplate`相关`jar包`的下载与使用步骤，[请点击这些文字去下载并学会简单使用](https://blog.csdn.net/lesileqin/article/details/112465651)

创建一个数据库，再创建一张表，添加几条记录

```sql
create database db1;
use db1;
create table account(
	id int primary key auto_increment,
	name varchar(32),
	balance double
);
insert into account values
(null,'zhangsan',1000),
(null,'lisi',2500);
```
前往`Druid`配置文件中更改连接数据库的`url`：

```java
url=jdbc:mysql://localhost:3306/db1?serverTimezone=GMT
```
使用`Junit`测试写好的每一个方法

# 一、修改操作
需求：修改`id`为`1`的`balance`字段为4500

```java
    @Test
    public void test1() {
   	 	JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
        String sql = "update account set balance = ? where id = ?";
        int cnt = template.update(sql,4500, 1);
        System.out.println(cnt);
    }
```

# 二、添加操作
需求：添加一条记录

```java
    @Test
    public void test2(){
    	JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
        String sql = "insert into account values(null,?,?)";
        int cnt = template.update(sql,"wangsan",100);
        System.out.println(cnt);
    }
```
# 三、删除操作
需求：删除`id=3`的记录
```java
    @Test
    public void test3(){
   		JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
        String sql = "delete from account where id = ?";
        int cnt = template.update(sql, 5);
        System.out.println(cnt);
    }
```
可以发现，上面三段代码都有相似的操作：

```java
JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
```
获取`JdbcTemplate`对象的操作，可以把这句代码抽取到类里面，定义为私有的变量：

```java
private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
```
这样以后再在这个类中写代码，就可以省略这些这一步骤，直接使用了


----------
[JdbcTemplate 执行 DQL 查询操作](https://blog.csdn.net/lesileqin/article/details/112475947)
