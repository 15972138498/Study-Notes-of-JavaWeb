@[toc]

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

在新建的类中，声明一个私有的成员变，方便下面写代码调用：

```java
private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
```

使用`Junit`测试写好的每一个方法

# 一、queryForMap 将查询结果封装为Map集合
`queryForMap`将列名作为`key`，将值作为`value`，将这条记录封装为一个`Map`集合

**注意：这个方法查询的结果集长度只能是1**

需求：查询 `id = 1` 的记录，将其封装为Map集合

```java
    @Test
    public void test4(){
        String sql = "select * from account where id = ?";
        //下面的templaet已经声明
        Map<String, Object> stringObjectMap = template.queryForMap(sql, 1);
        System.out.println(stringObjectMap);
    }
```
输出信息：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210111165207371.png)

# 二、queryForList 将查询结果封装为List集合
`queryForList`将每一条记录封装为一个`Map`集合，再将`Map`集合装载到`List`集合中

需求：查询所有记录，将其封装为 `List`

```java
    @Test
    public void test5(){
        String sql = "select * from account";
        List<Map<String, Object>> list = template.queryForList(sql);
        for(Map<String,Object> map : list){
            System.out.println(map);
        }
    }
```
输出信息：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210111165309479.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)


# 三、query 将查询结果封装为JavaBean的List集合
首先创建一个`JavaBean`类，名字叫做`Account`，声明表中包含的所有字段：

```java
    private Integer id;
    private String name;
    private Double balance;
```
在`IDEA`中按下快捷键`alt + Insert`声明所有的`get`、`set`和`toString`方法

需求：查询所有记录，将其封装为 Account 对象的 List 集合

```java
@Test
    public void test6_1(){
        String sql = "select * from account";
        List<Account> list = template.query(sql, new RowMapper<Account>() {
            @Override
            public Account mapRow(ResultSet rs, int i) throws SQLException {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double balance = rs.getDouble("balance");
                Account account = new Account();
                account.setId(id);
                account.setName(name);
                account.setBalance(balance);
                return account;
            }
        });
        for(Account account : list){
            System.out.println(account);
        }
    }
```
输出信息：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210111165711658.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
虽然达到了目标，但是上面的代码写的实在太多了！

使用`BeanPropertyRowMapper`实现类，可以完成`JavaBean`的自动封装，格式如下：

```java
new BeanPropertyRowMapper<类型>(类型.class)
```
实现查询的代码如下：

```java
    @Test
    public void test6_2(){
        String sql = "select * from account";
        List<Account> list = template.query(sql, new BeanPropertyRowMapper<Account>(Account.class));
        for(Account account : list){
            System.out.println(account);
        }
    }
```
也达到了一样的效果：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210111165711658.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)

# 四、queryForObject 将查询结果封装为Object的对象
`queryForObject`一般用于聚合函数的查询

需求：查询表中的总记录数

```java
   @Test
    public void test7(){
        String sql = "select count(id) from account";
        Long total = template.queryForObject(sql, Long.class);
        System.out.println(total);
    }
```
输出信息：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210111170050420.png)

-------
[JdbcTemplate 执行 DML 增删改操作](https://blog.csdn.net/lesileqin/article/details/112470234)
