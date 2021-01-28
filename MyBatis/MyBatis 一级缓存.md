@[toc]

---
[MyBatis 缓存简介与原理请点我~](https://blog.csdn.net/lesileqin/article/details/113348677) 
# 一、准备工作
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
# 二、测试
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
