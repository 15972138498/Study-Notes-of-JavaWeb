@[toc]

----
# 〇、准备工作
数据库：

```sql
create database MyBatis_DB;
use MyBatis_DB;
create table user(
	id int primary key auto_increment,
	name varchar(32) not null,
	pwd varchar(32) not null
);
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210123154108426.png)
然后随随便便插入几条数据

接下来创建一个`Maven`项目，注入`MySql`、`MyBatis`、`Junit`、[`LOG4J`](https://blog.csdn.net/lesileqin/article/details/113005859)依赖

创建表`User`实体类，再创建一个`UserMapper`接口，`UserMapper.xml`以及测试类
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210123154541561.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
接下来开始实现分页查询
# 一、Limit 实现分页
第一步、编写`UserMapper`接口
```java
List<User> findUserByLimit(Map<String,Integer> map);
```
第二步、编写`UserMapper.xml`文件，写`sql`，这里的接收参数是一个`Map`：
```xml
    <select id="findUserByLimit" resultType="User" parameterType="map">
        select * from mybatis_db.user limit #{startPage},#{Page}
    </select>
```
第三步、编写测试类，首先搞一个`Map`存储要传送的参数，然后调用方法，输出结果，最后关闭连接
```java
    //获取SqlSession
    private SqlSession sqlSession = MyBatisUtils.getSqlSession();
    //获取mapper
    private UserMapper mapper = sqlSession.getMapper(UserMapper.class);   
    //limit分页
    @Test
    public void findUserByLimitTest(){
        Map<String,Integer> map = new HashMap<String, Integer>();
        map.put("startPage",0);
        map.put("Page",5);
        List<User> userByLimit = mapper.findUserByLimit(map);
        for (User user : userByLimit) {
            System.out.println(user);
        }
        sqlSession.close();
    }
```
输出结果：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210123154912877.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
# 二、RowBounds 实现分页 | 不推荐使用

第一步、编写接口
```java
List<User> findUserByRowBounds();
```
第二步、编写`XML`：
```xml
    <select id="findUserByRowBounds" resultType="User">
        select * from mybatis_db.user;
    </select>
```
第三步、编写测试类
```java
    //获取SqlSession
    private SqlSession sqlSession = MyBatisUtils.getSqlSession();
    //RowBounds实现分页
    @Test
    public void findUserByRowBoundsTest(){
        //RowBounds实现
        RowBounds rowBounds = new RowBounds(0, 5);
        List<User> UserList = sqlSession.selectList(
                "com.wzq.mapper.UserMapper.findUserByRowBounds",
                null, rowBounds);
        for (User user : UserList) {
            System.out.println(user);
        }
        sqlSession.close();
    }
```
测试：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210123160426634.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)

