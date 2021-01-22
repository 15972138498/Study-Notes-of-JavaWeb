@[toc]

---
# 一、配置总览

`MyBatis`的配置会影响`MyBatis`行为的设置和属性信息，下图是配置文件中的各个属性：
![在这里插入图片描述](https://img-blog.csdnimg.cn/2021012215475667.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
**需要注意的是，在`mybatis-config.xml`中，这些属性必须按照上面的顺序编写！**

# 二、各个配置作用
## 1、properties 属性
这个配置的作用在于引用外部配置文件，可以新建一个`.properties`文件，把一些连接数据库的配置信息写入该文件，然后用`properties`属性引入进去就好了，演示如下：

新建一个配置文件：`db.properties`：

```xml
driver=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://localhost:3306/MyBatis_DB?serverTimezone=GMT
username=root
password=root
```
然后在`mybatis-config.xml`编写该属性：
```xml
    <!-- properties 引入外部配置文件 -->
    <properties resource="db.properties">
        <!-- 
        也可以在这里直接写
         <property name="xxxx" value="xxxx" />
         -->
    </properties>
```
如果一个属性在不只一个地方进行了配置，那么，MyBatis 将按照下面的顺序来加载：

- 首先读取在 properties 元素体内指定的属性。
- 然后根据 properties 元素中的 resource 属性读取类路径下属性文件，或根据 url 属性指定的路径读取属性文件，并覆盖之前读取过的同名属性。
- 最后读取作为方法参数传递的属性，并覆盖之前读取过的同名属性。
## 2、settings 设置
`settings`是`Mybatis`全局的一些配置，`MyBatis`中已经规定了一些默认的配置，如果需要其他的配置，那么再在`settings`标签内写就好。

一个配置完整的`settings`元素的示例如下：
```xml
<settings>
  <setting name="cacheEnabled" value="true"/>
  <setting name="lazyLoadingEnabled" value="true"/>
  <setting name="multipleResultSetsEnabled" value="true"/>
  <setting name="useColumnLabel" value="true"/>
  <setting name="useGeneratedKeys" value="false"/>
  <setting name="autoMappingBehavior" value="PARTIAL"/>
  <setting name="autoMappingUnknownColumnBehavior" value="WARNING"/>
  <setting name="defaultExecutorType" value="SIMPLE"/>
  <setting name="defaultStatementTimeout" value="25"/>
  <setting name="defaultFetchSize" value="100"/>
  <setting name="safeRowBoundsEnabled" value="false"/>
  <setting name="mapUnderscoreToCamelCase" value="false"/>
  <setting name="localCacheScope" value="SESSION"/>
  <setting name="jdbcTypeForNull" value="OTHER"/>
  <setting name="lazyLoadTriggerMethods" value="equals,clone,hashCode,toString"/>
</settings>
```
下表列出了几个比较重要的配置：

| 设置名 | 描述 | 有效值 | 默认值 |
| -- | -- | -- | -- |
| `cacheEnabled` | 全局性的开启或关闭所有映射器配置文件中<br>已配置的任何缓存 | `true | false` | `true` |
| `lazyLoadingEnabled` | 延迟加载的全局开关 | `true | false` | `false` |
| `logImpl` | 指定`MyBatis`所用日志的具体实现，未指定时将自动查找 | `SLF4J | LOG4J | LOG4J2 | JDK_LOGGING | COMMONS_LOGGING | NO_LOGGING` | 未设置 |

## 3、typeAliases 类型别名
写`MyBatis`的`xml`文件时，每次填写`returnType`都要填写实体类的全类名，比如：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210122160942591.png)
这真的超级麻烦，`typeAliases`的作用仅仅是为这些全类名起一个别名，简化书写

它共有三种起别名的方式：

- 为单个实体类取别名
  ```xml
    <typeAliases>
        	<typeAlias alias="User" type="com.wzq.domain.User" />
    </typeAliases>
   ```
- 指定一个包名，将该包下的所有实体类全部转换起别名（默认为类名首字母小写）
   ```xml
       <typeAliases>
        	<package name="com.wzq.domain"/>
       </typeAliases>
   ```
- 使用注解（**上一步需要写**），可以在类名上面添加注解起别名
   ```java
   @Alias("User")
   public class User{ 
   	...
   }
   ```

## 4、typeHandlers 类型处理器
`MyBatis` 在设置预处理语句（`PreparedStatement`）中的参数或从结果集中取出一个值时， 都会用类型处理器将获取到的值以合适的方式转换成 `Java` 类型。下表描述了一些默认的类型处理器。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210122162205783.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210122162231794.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)

## 5、objectFactory 对象工厂
每次 `MyBatis` 创建结果对象的新实例时，它都会使用一个对象工厂（`ObjectFactory`）实例来完成实例化工作。 默认的对象工厂需要做的仅仅是实例化目标类，要么通过默认无参构造方法，要么通过存在的参数映射来调用带有参数的构造方法。 如果想覆盖对象工厂的默认行为，可以通过创建自己的对象工厂来实现。
## 6、plugins 插件
`MyBatis`的插件功能还是很强大的，也有很多的扩展，比如：

- `mybatis-generator-core`
- `mybatis-plus`
- 通用`mapper`
- 等等
## 7、environments 环境配置
`MyBatis`可以配置多套环境，但是：**尽管可以配置多个环境，但每个 SqlSessionFactory 实例只能选择一种环境。**


```xml
<!-- default字段定义了当前使用的是哪一套配置 -->
<environments default="development">
  <environment id="development">
    <!-- 使用JDBC的事务管理 -->
    <transactionManager type="JDBC">
      <property name="..." value="..."/>
    </transactionManager>
    <!-- type = "POOLED" 规定了这里有数据库链接池 -->
    <dataSource type="POOLED">
      <property name="driver" value="${driver}"/>
      <property name="url" value="${url}"/>
      <property name="username" value="${username}"/>
      <property name="password" value="${password}"/>
    </dataSource>
  </environment>
  <!-- 另一套配置 -->
  <environment id="test">
    <transactionManager type="JDBC">
      <property name="..." value="..."/>
    </transactionManager>
    <dataSource type="POOLED">
      <property name="driver" value="${driver}"/>
      <property name="url" value="${url}"/>
      <property name="username" value="${username}"/>
      <property name="password" value="${password}"/>
    </dataSource>
  </environment>
</environments>
```

## 8、mappers 映射器
`mappers`的作用是：注册绑定`Mapper`文件，它共有四种方式绑定

方式一：使用`resource`
```xml
    <mappers>
        <mapper resource="com/wzq/mapper/UserMapper.xml"/>
    </mappers>
```
方式二：使用`class`
```xml
    <mappers>
        <mapper class="com.wzq.mapper.UserMapper"/>
    </mappers>
```
**但是这里需要`Mapper`与`Mapper.xml`文件共处于一个包下，并且同名，方式三也是！**

方式三：将包内的映射器接口实现全部注册为映射器
```xml
	<mappers>
  		<package name="org.mybatis.builder"/>
	</mappers>
```
方式四是利用`url`绑定的，用的比较少，这里不再列出！
