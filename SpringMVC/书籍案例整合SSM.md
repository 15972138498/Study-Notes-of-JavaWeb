@[toc]

-----

# 一、搭建环境
## 1、搭建数据库
```sql
#创建数据库
create database ssmbuild;
use ssmbuild;
#创建表
create table books(
	bookID int primary key auto_incrment comment '书的ID',
	booNanme varchar(100) not null comment '书名',
	bookCounts int not null comment '书的数量',
	detail varchar(200) not null comment '书的描述'
);
#添加几条记录
insert into books values
(null,'Java',1,'从入门到放弃'),
(null,'MySQL',100,'从删库到跑路'),
(null,'Linux',5,'从进门到进牢');
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210210084705989.png)
## 2、在idea中创建Maven项目
打开`idea`创建不使用骨架的`Maven`项目，打开`pom.xml`导入依赖包：
```xml
<!-- 导入依赖 -->
<dependencies>
    <!-- Junit -->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.11</version>
    </dependency>
    <!-- MySQL 数据库驱动 -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.15</version>
    </dependency>
    <!-- 连接池 -->
    <!-- https://mvnrepository.com/artifact/com.mchange/c3p0 -->
    <dependency>
        <groupId>com.mchange</groupId>
        <artifactId>c3p0</artifactId>
        <version>0.9.5.2</version>
    </dependency>
    <!-- servlet -->
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>servlet-api</artifactId>
        <version>2.5</version>
    </dependency>
    <!-- jsp -->
    <dependency>
        <groupId>javax.servlet.jsp</groupId>
        <artifactId>jsp-api</artifactId>
        <version>2.2</version>
    </dependency>
    <!-- jstl -->
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>jstl</artifactId>
        <version>1.2</version>
    </dependency>
    <!-- mybatis -->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.5.6</version>
    </dependency>
    <!-- mybatis-spring -->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis-spring</artifactId>
        <version>2.0.6</version>
    </dependency>
    <!-- Spring-jdbc -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-jdbc</artifactId>
        <version>5.3.3</version>
    </dependency>
    <!-- Spring WebMVC -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.2.12.RELEASE</version>
    </dependency>
</dependencies>
```
同样在`pom.xml`配置静态资源导出问题：
```xml
<!-- 静态资源导出问题 -->
<build>
    <resources>
        <resource>
            <directory>src/main/java</directory>
            <includes>
                <include>**/*.properties</include>
                <include>**/*.xml</include>
            </includes>
            <filtering>false</filtering>
        </resource>
        <resource>
            <directory>src/main/resources</directory>
            <includes>
                <include>**/*.properties</include>
                <include>**/*.xml</include>
            </includes>
            <filtering>false</filtering>
        </resource>
    </resources>
</build>
```

然后为该项目添加`web`的支持，然后打开项目结构，新建`lib`目录，把这些包导入到最后生成的文件中，最后配置`Tomcat`

## 3、项目结构
新建包如下图所示：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210210085133542.png)
在`pojo`下新建`books`的实体类：
```java
package com.wzq.pojo;

public class Books {
    private int bookID;
    private String bookName;
    private int bookCounts;
    private String detail;
	/*
		有参、无参、get、set、toString
	*/
}

```
# 二、dao层 | MyBatis
## 1、mybatis-config.xml
在`resource`下新建`database.properties`用于连接数据库：
```xml
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/ssmbuild?serverTimezone=GMT
jdbc.username=root
jdbc.password=root
```
新建`MyBatis`配置文件`mybatis-config.xml`：
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
	<!-- 起别名 -->
    <typeAliases>
        <package name="com.wzq.pojo"/>
    </typeAliases>
    
    <!-- 连接文件在Spring中去配置 -->

	<!-- 绑定mapper -->
    <mappers>
        <mapper class="com.wzq.dao.BookMapper"/>
    </mappers>
    
</configuration>
```
## 2、spring-dao.xml
新建`spring-dao.xml`，在这里面将要整合`MyBatis`与`Spring`：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <!-- 1.关联数据库配置文件 -->
    <context:property-placeholder location="classpath:database.properties"/>

    <!-- 2.c3p0数据库连接池 -->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="${jdbc.driver}"/>
        <property name="jdbcUrl" value="${jdbc.url}"/>
        <property name="user" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>

        <!-- c3p0连接池的私有属性 -->
        <property name="maxPoolSize" value="30"/>
        <property name="minPoolSize" value="10"/>
        <!-- 关闭连接后不自动commit -->
        <property name="autoCommitOnClose" value="false"/>
        <!-- 获取连接超时时间 -->
        <property name="checkoutTimeout" value="10000"/>
        <!-- 当获取连接失败时，尝试的次数 -->
        <property name="acquireRetryAttempts" value="2"/>
    </bean>

    <!-- 3.sqlSessionFactory -->
    <bean name="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <!-- 绑定mybatis的配置文件 -->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
    </bean>

    <!-- 4.配置dao接口扫描包，动态的实现了dao接口可以注入到Spring容器中 -->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <!-- 注入sqlSessionFactory -->
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
        <!-- 扫描dao包 -->
        <property name="basePackage" value="com.wzq.dao"/>
    </bean>
</beans>
```
## 3、MyBatis的Mapper以及对应的xml
接下来就是`MyBatis`的工作了

在`com.wzq.dao`下新建接口：`BookMapper`：
```java
package com.wzq.dao;

import com.wzq.pojo.Books;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

public interface BookMapper {
    //增加一本书
    int addBook(Books books);

    //删除一本书
    int deleteBookById(@Param("id") int id);

    //更新一本书
    int updateBook(Books books);

    //查询一本书
    Books queryBookById(@Param("id") int id);

    //查询所有书
    List<Books> queryAllBook();

    //根据name属性模糊查询
    List<Books> queryBookByName(@Param("name") String name);
}
```
对应的`*Mapper.xml`：
```xml
<?xml version="1.0" encoding="GBK" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.wzq.dao.BookMapper">

    <!-- 增加一本书 -->
    <insert id="addBook" parameterType="books">
        insert into ssmbuild.books
        values (null,#{bookName},#{bookCounts},#{detail})
    </insert>

    <!-- 删除一本书 -->
    <delete id="deleteBookById">
        delete from ssmbuild.books where bookID = #{id}
    </delete>

    <!-- 更新一本书 -->
    <update id="updateBook" parameterType="books">
        update books set bookName=#{bookName},bookCounts=#{bookCounts},detail = #{detail} where bookID = #{bookID};
    </update>

    <!-- 查询一本书 -->
    <select id="queryBookById" resultType="books">
        select * from ssmbuild.books where bookID = #{id}
    </select>

    <!-- 查询所有书 -->
    <select id="queryAllBook" resultType="books">
        select * from ssmbuild.books
    </select>

    <!-- 通过名称查询书 -->
    <select id="queryBookByName" resultType="books">
        select * from ssmbuild.books where bookName like #{name}
    </select>
</mapper>
```
# 三、service层 
## 1、spring-service.xml
新建`spring-service.xml`：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd">

    <!-- 1.扫描service的包 -->
    <context:component-scan base-package="com.wzq.service"/>

    <!-- 2.所有业务类注入到Spring，通过注解实现 -->

    <!-- 3.声明式事务 -->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <!-- 注入数据源 -->
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!-- 这里可以加aop事务支持 -->
</beans>
```
## 2、service层接口与其实现
在`com.wzq.service`下，新建`BookService`接口：
```java
package com.wzq.service;

import com.wzq.pojo.Books;

import java.util.List;

public interface BookService {
    //service层调dao层
    //增加一本书
    int addBook(Books books);

    //删除一本书
    int deleteBookById(int id);

    //更新一本书
    int updateBook(Books books);

    //查询一本书
    Books queryBookById(int id);

    //查询所有书
    List<Books> queryAllBook();

    //根据name属性模糊查询
    List<Books> queryBookByName(String name);
}
```
对应的实现类，在这里使用注解配为`bean`
```java
package com.wzq.service;

import com.wzq.dao.BookMapper;
import com.wzq.pojo.Books;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private BookMapper bookMapper;

    @Autowired
    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public int addBook(Books books) {
        return bookMapper.addBook(books);
    }

    public int deleteBookById(int id) {
        return bookMapper.deleteBookById(id);
    }

    public int updateBook(Books books) {
        return bookMapper.updateBook(books);
    }

    public Books queryBookById(int id) {
        return bookMapper.queryBookById(id);
    }

    public List<Books> queryAllBook() {
        return bookMapper.queryAllBook();
    }

    public List<Books> queryBookByName(String name) {
        return bookMapper.queryBookByName("%"+name+"%");
    }
}
```
# 四、controller层 | Spring MVC
## 1、web.xml
打开`web.xml`，在这里配置`DispatchServlet`和乱码过滤以及`session`超时时间：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    
    <!-- DispatchServlet -->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:applicationContext.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
    
    <!-- 乱码过滤 -->
    <filter>
        <filter-name>encodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>utf-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

    <!-- Session -->
    <session-config>
        <session-timeout>15</session-timeout>
    </session-config>
</web-app>
```
## 2、spring-mvc.xml
在`resource`下创建`spring-mvc.xml`，在这里加载注解驱动，配置静态资源过滤、扫描`controller`层的包以及视图层的解析：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc
        https://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!-- 1.注解驱动 -->
    <mvc:annotation-driven/>

    <!-- 2.静态资源过滤 -->
    <mvc:default-servlet-handler/>

    <!-- 3.扫描包：controller层 -->
    <context:component-scan base-package="com.wzq.controller"/>

    <!-- 4.视图解析器 -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <!-- 前缀 -->
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <!-- 后缀 -->
        <property name="suffix" value=".jsp"/>
    </bean>
</beans>
```
## 3、controller
在`com.wzq.controller`下新建`BookController`，所有`Book`有关的请求将走这里：
```java
package com.wzq.controller;

import com.wzq.pojo.Books;
import com.wzq.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    //controller层调service层
    @Autowired
    private BookService bookService;

    //查询所有书籍
    @RequestMapping("/allBook")
    public String AllBook(Model model){
        List<Books> list = bookService.queryAllBook();
        model.addAttribute("list",list);
        return "allBook";
    }

    //跳转到增加书籍页面
    @RequestMapping("/toAddBook")
    public String toAddBook(){
        return "addBook";
    }

    //添加书籍
    @RequestMapping("/addBook")
    public String addBook(Books book){
        System.out.println(book);
        bookService.addBook(book);
        return "redirect:/book/allBook";
    }

    //跳转到修改书籍页面
    @RequestMapping("/toUpdateBook")
    public String toUpdateBook(int id, Model model){
        System.out.println("BookController => updateBook => id = " + id);
        Books book = bookService.queryBookById(id);
        System.out.println("BookController => updateBook => book = " + book);
        //数据回显
        model.addAttribute("book",book);
        return "updateBook";
    }

    //修改书籍
    @RequestMapping("/updateBook")
    public String updateBook(Books book){
        bookService.updateBook(book);
        return "redirect:/book/allBook";
    }

    //删除书籍
    @RequestMapping("/deleteBook")
    public String deleteBook(int id){
        bookService.deleteBookById(id);
        return "redirect:/book/allBook";
    }

    //模糊查询
    @RequestMapping("/queryBookByName")
    public String queryBookByName(String name,Model model){
        List<Books> list = bookService.queryBookByName(name);
        model.addAttribute("list",list);
        return "allBook";
    }

}
```
## 4、整合三个和Spring有关的xml文件
新建一个文件`applicationContext.xml`，这里把`spring-dao.xml`、`spring-service.xml`、`spring-mvc.xml`导入进去，以后如果使用只需要加载`applicationContext.xml`就好了：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <import resource="classpath:spring-dao.xml"/>
    <import resource="classpath:spring-mvc.xml"/>
    <import resource="classpath:spring-service.xml"/>

</beans>
```
# 五、项目目录总览
至此`SSM`整合完毕（[`JSP`页面在博客里没有给出，如果读者有兴趣可以点击这串字下载源码](https://github.com/lesileqin/Study-Notes-of-JavaWeb/tree/master/SpringMVC/ssmbuild)）
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210210091149768.png)
