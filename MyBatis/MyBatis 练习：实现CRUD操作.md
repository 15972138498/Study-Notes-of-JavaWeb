@[toc]

---
# 一、需求
使用`MyBatis`实现在`MySql`数据库中的`CRUD`操作，使用`Junit`测试并在控制台输出操作结果
# 二、分析
技术选型：`MySql`+`Java`+`Maven`+`Mybatis`+`Junit`
# 三、实现步骤
## 1、在idea中创建项目过程
打开`mysql`数据库，新建一个数据库，并添加一个表，增加两条数据：

```sql
-- 如果不存咋mybatis_db数据库，则创建
create database if not exists mybatis_db;
-- 使用该数据库
use mybatis_db;
-- 创建user表
create TABLE user(
	id int primary key auto_increment,	-- 主键id自增长
	name varchar(32) not null,	-- 姓名字段，非空
	pwd varchar(32) not null, 	-- 密码字段，非空
);
-- 给user表添加两条数据
insert into mybatis_db.user values
(null,'张三','123456'),
(null,'李四','234567');
```

在`idea`中新建一个`Maven Project`，不使用任何骨架

在`pom.xml`中注入`MySql驱动`+`MyBatis`+`Junit`依赖：

```xml
    <dependencies>
        <!-- mysql  -->
        <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.15</version>
        </dependency>
        <!--MyBatis-->
        <!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.6</version>
        </dependency>
        <!--Junit-->
        <!-- https://mvnrepository.com/artifact/junit/junit -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
```
在该工程中新建一个模块（`Moudle`--`MyBatis-01`），该模块也为不使用骨架的`Maven`项目，新建的模块可以直接用父工程的`pom.xml`导入的依赖
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210122132152290.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
在`MyBatis-01`的`src`目录下的`resources`目录下新建一个`mybatis-config.xml`配置文件，该文件中设置`mysql驱动`、`url`和连接数据库的`username`和`password`：

```xml
<?xml version="1.0" encoding="GBK" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/MyBatis_DB?serverTimezone=GMT"/>
                <property name="username" value="root"/>
                <property name="password" value="root"/>
            </dataSource>
        </environment>
    </environments>

</configuration>
```
在`src\main`下新建包：数据库实体类包`com.wzq.domain`、MyBatis实现类包`com.wzq.mapper`、工具类包
`com.wzq.utils`，建好之后，目录结构应该是这样的：
![在这里插入图片描述](https://img-blog.csdnimg.cn/2021012213333918.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
## 2、编写实体类

在`com.wzq.domain`下，新建一个类`User`，该类为`user`表对应的实体类，所有变量均与表中字段保持一致，并添加`Getter and Setter`、有参的无参的构造方法，重新`toString`方法：

```java
package com.wzq.domain;

public class User {
    private Integer id;
    private String name;
    private String pwd;
    
    public User() {}
    public User(Integer id, String name, String pwd) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
```
## 3、编写 MyBatis 工具类
在`com.wzq.utils`包下，新建类`MyBatisUtils`，在该类中加载配置文件，并获取`SqlSession`对象：

```java
package com.wzq.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisUtils {

    private static SqlSessionFactory sqlSessionFactory = null;

    static {
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream  = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SqlSession getSqlSession(){
        return sqlSessionFactory.openSession();
    }
}
```
## 4、实现CRUD操作
在`com.wzq.mapper`中，新建一个接口`UserMapper`，该接口定义了操作`user`表的方法

```java
package com.wzq.mapper;

import com.wzq.domain.User;

import java.util.List;

public interface UserMapper {
}
```
新建一个`UserMapper.xml`文件，该文件中编写`sql`，`namespace`指向`UserMapper`接口，要写全类名，并把`encoding`设置为`GBK`，否则无法在注释里加中文
```xml
<?xml version="1.0" encoding="GBK" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.wzq.mapper.UserMapper">

</mapper>
```
最后一步在`mybatis-config.xml`中添加`mappers`标签，指定资源路径为`com/wzq/mapper/UserMapper.xml`：
```xml
    <mappers>
        <mapper resource="com/wzq/mapper/UserMapper.xml"/>
    </mappers>
```
### 1）查询所有用户
在`UserMapper`接口中，定义额查询所有用户的方法：
```java
List<User> findAll();
```
在`UserMapper.xml`中写`sql`语句，指定返回类型为`User`，写全类名
```xml
    <select id="findAll" resultType="com.wzq.domain.User">
        select * from mybatis_db.user
    </select>
```
在`test/java`下，创建包`com.wzq.mapper.UserMapperTest`测试类

获取`SqlSession`对象和`mapper`：

```java
    //抽取属性，获取SqlSession对象
    private SqlSession sqlSession = MyBatisUtils.getSqlSession();
    //抽取属性，获取mapper
    private UserMapper mapper = sqlSession.getMapper(UserMapper.class);
```
编写测试方法：
```java
    //测试查询所有用户
    @Test
    public void findAllTest(){
        //2. 执行
        List<User> all = mapper.findAll();
        for (User user : all) {
            System.out.println(user);
        }
        //3. 关闭连接
        sqlSession.close();
    }
```
测试，输出结果：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210122140258483.png)
没毛病
### 2）根据ID查询用户
同样的步骤，首先在`UserMapper`中，定义方法：
```java
User findUserById(int id);
```
在`UserMapper.xml`中写`sql`：
```xml
    <select id="findUserById" resultType="com.wzq.domain.User" parameterType="int">
        select * from mybatis_db.user where id = #{id}
    </select>
```
在`UserMapperTest`写测试方法：
```java
    //测试根据ID查询用户
    @Test
    public void findUserByIdTest(){
        //2. 执行
        User user = mapper.findUserById(5);
        System.out.println(user);
        //3. 关闭连接
        sqlSession.close();
    }
```
输出结果：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210122140532727.png)
### 3）插入、修改、删除用户
接下来就如法炮制了，首先在`UserMapper`接口中添加三个方法：

```java
    //插入一个用户
    void addUser(User user);

    //修改用户
    void updateUser(User user);

    //删除用户
    void deleteUser(int id);
```
在`UserMapper.xml`中写`sql`：
```xml
    <!-- 插入一个用户 -->
    <insert id="addUser" parameterType="com.wzq.domain.User">
        insert into mybatis_db.user (id,name,pwd) values (#{id},#{name},#{pwd})
    </insert>

    <!-- 修改用户 -->
    <update id="updateUser" parameterType="com.wzq.domain.User">
        update mybatis_db.user set name=#{name},pwd=#{pwd} where id=#{id} ;
    </update>

    <!-- 删除用户 -->
    <delete id="deleteUser" parameterType="int">
        delete from mybatis_db.user where id = #{id}
    </delete>
```
在`UserMapperTest`中编写测试方法：

**需要注意的是执行增、删、改需要提交事务！**

```java
    //增删改需要提交事务
    //插入一个用户
    @Test
    public void addUserTest(){
        User user = new User(null,"大王","1234215");
        mapper.addUser(user);
        //提交事务
        sqlSession.commit();
        findAllTest();
    }

    //修改用户
    @Test
    public void updateUserTest(){
        mapper.updateUser(new User(1,"王五","123456"));
        sqlSession.commit();
        sqlSession.close();
    }

    //删除用户
    @Test
    public void deleteUserTest(){
        mapper.deleteUser(2);
        sqlSession.commit();
        sqlSession.close();
    }
```
