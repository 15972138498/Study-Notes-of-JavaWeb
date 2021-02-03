@[toc]

-----
# 一、准备工作
## 1、创建表
首先需要一个表：
```sql
create database MyBatis_DB;
use MyBatis_DB;
create table user()(
	id int primary key auto_increment,
	name varchar(32) not null,
	pwd varchar(32) not null
);
```
再随便插入几条数据：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210202174202155.png)
## 2、导入依赖
创建`maven`工程，打开`pom.xml`导入相关`jar`包（`junit`、`mysql`、`mybatis`、`aspectjweaver`、`spring-webmvc`、`log4j`、`mybatis-spring`、`spring-jdbc`）：
```xml
<dependencies>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.11</version>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.15</version>
    </dependency>
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.5.6</version>
    </dependency>
    <dependency>
        <groupId>org.aspectj</groupId>
        <artifactId>aspectjweaver</artifactId>
        <version>1.9.4</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.2.12.RELEASE</version>
    </dependency>
    <dependency>
        <groupId>log4j</groupId>
        <artifactId>log4j</artifactId>
        <version>1.2.17</version>
    </dependency>
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis-spring</artifactId>
        <version>2.0.6</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.springframework/spring-jdbc -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-jdbc</artifactId>
        <version>5.3.3</version>
    </dependency>

</dependencies>
```
## 3、用户表的pojo类
创建`User`表的`pojo`类：
```java
package com.wzq.pojo;

public class User {
    private int id;
    private String name;
    private String pwd;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
## 4、创建MyBatis配置文件
```xml
<?xml version="1.0" encoding="GBK" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
	
	<!-- 使用log4j日志 -->
    <settings>
        <setting name="logImpl" value="LOG4J"/>
    </settings>

	<!-- 起别名 -->
    <typeAliases>
        <typeAlias type="com.wzq.pojo.User" alias="user"/>
    </typeAliases>
	
	<!-- 数据源与mapper在接下来的文件中配置 -->
	    
</configuration>
```
## 5、创建dao层接口与相应的xml
接口：
```java
package com.wzq.dao;

import com.wzq.pojo.User;

import java.util.List;

public interface UserMapper {
	//获取表中所有数据
    List<User> showAllUser();
}
```
`MyBatis`的`.xml`文件：
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.wzq.dao.UserMapper">

    <select id="showAllUser" resultType="user">
        select * from user;
    </select>

</mapper>
```
# 二、整合 MyBatis
## 1、获取数据源
新建`spring-dao.xml`，这是一个`bean`文件，下面获取数据源：
```xml
<?xml version="1.0" encoding="GBK"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        https://www.springframework.org/schema/aop/spring-aop.xsd">

    <!-- DataSource : 使用Spring的数据源代替MyBatis 的配置
        这里使用Spring提供的JDBC：org.springframework.jdbc.datasource.DriverManagerDataSource
    -->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/mybatis_db?serverTimezone=GMT"/>
        <property name="username" value="root"/>
        <property name="password" value="root"/>
    </bean>

</beans>
```
## 2、获取sqlSessionFactory与sqlSessionTemplate
还是在这个文件里，获取`sqlSessionFactory`与`sqlSessionTemplate`，

注意：在这里获取之后，就不用写之前的工具类了，`SqlSessionTemplate`与`SqlSession`等价，建议使用`SqlSessionTemplate`，因为它比`SqlSession`多了安全机制

```xml
<?xml version="1.0" encoding="GBK"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        https://www.springframework.org/schema/aop/spring-aop.xsd">

    <!-- DataSource : 使用Spring的数据源代替MyBatis 的配置
        这里使用Spring提供的JDBC：
    -->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/mybatis_db?serverTimezone=GMT"/>
        <property name="username" value="root"/>
        <property name="password" value="wzq99121%%"/>
    </bean>

    <!-- sqlSessionFactory -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <!-- 绑定MyBatis配置文件 -->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
        <property name="mapperLocations" value="classpath:com/wzq/dao/*.xml"/>
    </bean>

    <!-- SqlSessionTemplate 就是我们使用的SqlSession -->
    <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
        <!-- 只能使用构造器注入SqlSessionFactory，因为没有set方法 -->
        <constructor-arg index="0" ref="sqlSessionFactory"/>
    </bean>
</beans>
```
## 3、UserMapper实现类
因为我们如果想要把某个类托管给`Spring`（就是注册为`bean`），所以它必须要有自己的实现类：

```java
package com.wzq.dao;

import com.wzq.pojo.User;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

public class UserMapperImpl implements UserMapper {

	//声明sqlSessionTemplate，习惯起名字叫做sqlSession
    private SqlSessionTemplate sqlSession;

	//set方法，在bean里面注入即可
    public void setSqlSession(SqlSessionTemplate sqlSession){
        this.sqlSession = sqlSession;
    }
	
	//实现方法
    public List<User> showAllUser() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        return mapper.showAllUser();
    }
}
```
这样写，明显又麻烦了不少，所以`MyBatis`提供了一个`SqlSessionDaoSupport`，它是个抽象的支持类，也可以用来提供`sqlSession`，使用它，在`spring-dao.xml`中，可以不注册：`sqlSessionTemplate`，实现`UserMapper`的接口也可以简写为：

```java
package com.wzq.dao;

import com.wzq.pojo.User;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import java.util.List;

public class UserMapperImpl extends SqlSessionDaoSupport implements UserMapper {

    public List<User> showAllUser() {
        return getSqlSession().getMapper(UserMapper.class).showAllUser();
    }
}
```
一下子少了超级多行，需要注意的是这个类又继承了`SqlSessionDaoSupport`

## 4、注册为bean
现在把上面的实现类注册为`bean`：

如果使用`sqlSessionDaoSupport`：
```xml
<?xml version="1.0" encoding="GBK"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        https://www.springframework.org/schema/aop/spring-aop.xsd">

    <!-- 引入xml文件 -->
    <import resource="spring-dao.xml" />

    <!-- 注册UserMapper的实现类 -->
    <bean id="userMapper" class="com.wzq.dao.UserMapperImpl" >
        <property name="sqlSessionFactory" ref="sqlSessionFactory" />
    </bean>

</beans>
```
不使用的话，需要把属性改一下：
```xml
<?xml version="1.0" encoding="GBK"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        https://www.springframework.org/schema/aop/spring-aop.xsd">

    <!-- 引入xml文件 -->
    <import resource="spring-dao.xml" />

    <!-- 注册UserMapper的实现类 -->
    <bean id="userMapper" class="com.wzq.dao.UserMapperImpl" >
        <property name="sqlSession" ref="sqlSession" />
    </bean>

</beans>
```
# 三、测试
这就写完了，看一下目录结构:
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210203160708492.png)
写一个类测试一下:
```java
import com.wzq.dao.UserMapper;
import com.wzq.dao.UserMapperImpl;
import com.wzq.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    @Test
    public void Test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper userMapper = context.getBean("userMapper", UserMapper.class);
        for (User user : userMapper.showAllUser()) {
            System.out.println(user);
        }
    }
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210203160743523.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
成功获取所有信息！
