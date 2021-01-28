
@[toc]

------


> [MyBatis 缓存及原理](https://blog.csdn.net/lesileqin/article/details/113348677)
> [MyBatis 一级缓存](https://blog.csdn.net/lesileqin/article/details/113352616)
> [MyBatis 二级缓存](https://blog.csdn.net/lesileqin/article/details/113356160)

# 一、二级缓存简介
二级缓存也叫全局缓存，由于一级缓存的作用域太低了，所以就诞生了二级缓存

它是基于`mapper`级别的缓存，一个`***mapper.xml`文件对应一个二级缓存

工作机制：

- 一个会话查询一条数据，这个数据就会被放在当前会话的一级缓存中；
- 如果当前会话关闭了，这个会话对应的一级缓存就会传送到二级缓存
- 新的会话查询信息，就可以从二级缓存中获取内容
- 不同的`mapper`查出的数据会放在自己对应的缓存中
# 二、准备工作
## 1、表
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210128173240617.png)
## 2、表对应的实体类
```java
package com.wzq.pojo;

public class User {
    private int id;
    private String name;
    private String pwd;

    public User() {
    }

    public User(int id, String name, String pwd) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
    }

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
## 3、dao层接口
```java
package com.wzq.dao;

import com.wzq.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    
    User findUserById(@Param("id") int id);
        
}
```
## 4、接口对应的xml
```xml
<?xml version="1.0" encoding="GBK"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.wzq.dao.UserMapper">
	
    <select id="findUserById" parameterType="_int" resultType="User">
        select * from user where id = #{id}
    </select>

</mapper>
```
# 三、二级缓存步骤
## 1、开启全局缓存
在`mybatis-config.xml`文件中，添加：
```xml
<settings>
    <!-- 设置日志 -->
    <setting name="logImpl" value="LOG4J"/>
    <!-- 开启全局缓存（二级缓存） -->
    <setting name="cacheEnabled" value="true"/>
</settings>
```
## 2、在要使用二级缓存的Mapper中开启
如果某个`,mapper`需要使用缓存，只需要在对应的`***Mapper.xml`文件中添加：

```xml
<cache />
```
就好了

如果这个世界这么简单就好了……

遗憾的是，只写一个`<cache />`标签会报错，解决这个报错需要自己自定义缓存（实现`MyBatis`缓存接口），所以，为了简单起见，可以直接拿官网给的例子：

```xml
<cache
  eviction="FIFO"
  flushInterval="60000"
  size="512"
  readOnly="true"/>
```

这样就不会报错了

# 四、测试
测试类：
```java
package com.wzq.daoTest;

import com.wzq.dao.UserMapper;
import com.wzq.pojo.User;
import com.wzq.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class UserMapperTest {
    @Test
    public void findUserByIdTest(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User user1 = mapper.findUserById(5);
        System.out.println(user1);
		System.out.println("===============================================================");
		
        User user2 = mapper.findUserById(5);
        System.out.println(user2);

        sqlSession.close();
    }
}
```
运行：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210128174219770.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
可以看到，在同一个`SqlSession`中，调用两次`findUserById(5)`，只执行了一次查询数据库的动作，可以看出来第一次执行完毕，就把结果存入缓存中了

那如果第二次查`6号用户`：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210128174600717.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
因为缓存中没有`5号`，所以就又去查了数据库
