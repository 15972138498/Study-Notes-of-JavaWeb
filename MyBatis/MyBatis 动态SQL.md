@[toc]

----
# 一、搭建环境

创建数据库：
```sql
create database MyBatis_DB;
use MyBatis_DB;
create table blog
(
    id          varchar(50)  not null comment '博客id',
    title       varchar(100) not null comment '博客标题',
    author      varchar(30)  not null comment '博客作者',
    create_time datetime     not null comment '创建时间',
    views       int(30)      not null comment '浏览量'
)   charset = utf8;
```
稍等利用`MyBatis`插入数据，现在打开`IDEA`创建`Maven`工程，在`pom.xml`文件中注入`MyBatis`、`Junit`、`MySQL`、`log4j`依赖：
```xml
<dependencies>
    <!-- mysql -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.15</version>
    </dependency>
    <!-- mybatis -->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.5.6</version>
    </dependency>
    <!-- junit  -->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
        <scope>test</scope>
    </dependency>
    <!-- log4j -->
    <dependency>
        <groupId>log4j</groupId>
        <artifactId>log4j</artifactId>
        <version>1.2.17</version>
    </dependency>
</dependencies>
```
创建`MyBatis`配置文件`myabtis-config.xml`

创建数据库对应的实体类`Blog`，`set和get`方法，不再贴出，对应数据库字段名

创建生成随机`ID`的工具类：
```java
import java.util.UUID;

public class IDutils {
    public static String getId(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
```
创建`MyBatisUIils`工具类：
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
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession(true);
    }
}
```
随后创建`BlogMapper`和`BlogMapper.xml`在`mybatis-config.xml`中注册

然后在`BlogMapper`接口中定义插入数据的方法：

```java
int addBlog(Blog blog);
```
在`BlogMapper.xml`中写`sql`：
```xml
<insert id="addBlog" parameterType="Blog">
    insert into blog values (#{id},#{title},#{author},#{create_time},#{views});
</insert>
```
创建测试类插入数据：
```java
@Test
public void addBlogTest(){
    SqlSession sqlSession = MyBatisUtils.getSqlSession();
    BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
    Blog blog = new Blog();
    blog.setId(IDutils.getId());
    blog.setTitle("Mybatis");
    blog.setAuthor("狂神说");
    blog.setCreate_time(new Date());
    blog.setViews(9999);

    mapper.addBlog(blog);

    blog.setId(IDutils.getId());
    blog.setTitle("Java");
    mapper.addBlog(blog);

    blog.setId(IDutils.getId());
    blog.setTitle("Spring");
    mapper.addBlog(blog);

    blog.setId(IDutils.getId());
    blog.setTitle("微服务");
    mapper.addBlog(blog);
    sqlSession.close();
}
```
数据插入成功：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210127152211227.png)
（[听的狂神秦疆老师的课，吹一波彩虹屁，讲的超级好！](https://space.bilibili.com/95256449?spm_id_from=333.788.b_765f7570696e666f.1)）
# 二、if
`if`标签就是用来判断的，它的语法格式是这样的（类似`java`的`if`语句）：
```xml
<if test = "条件">
	...
</if>
```
可能说着比较抽象，下面通过一个需求实践一波这个`if`标签：

**使用`if`标签，查询所有`author`字段带`“狂”`的和`title`字段带有`'a'`的**

首先肯定是在`BlogMapper`接口中定义查询的方法：
```java
List<Blog> queryBlogByIf(Map map);
```
然后开始在`BlogMapper.xml`中写带有`if标签`的动态`SQL`了：

```xml
<select id="queryBlogByIf" parameterType="map" resultType="Blog">
    <!-- 最后的 1 = 1 是为了后面的and好拼接 -->
    select * from blog where 1 = 1
    <!-- 如果传入的author字段不为空，则插入下面的sql -->
    <if test="author != null">
        and author like #{author}
    </if>
    <!-- 同上 -->
    <if test="title != null">
        and title like #{title}
    </if>
</select>
```
最后测试：
```java
@Test
public void queryBlogByIfTest(){
    SqlSession sqlSession = MyBatisUtils.getSqlSession();
    BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

    Map map = new HashMap();
    map.put("author","%狂%");
    map.put("title","%a%");
    List<Blog> blogs = mapper.queryBlogByIf(map);
    for (Blog blog : blogs) {
        System.out.println(blog);
    }
    
    sqlSession.close();
}
```
看一下结果：
![在这里插入图片描述](https://img-blog.csdnimg.cn/2021012715330777.png)
可以看到已经拼接好的`sql`：
```sql
select * from blog where 1 = 1 and author like ? and title like ?
```
如果只传入一个参数（**把测试类的第一个map.put(...)删去**），结果如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210127153503985.png)
可以看到`sql`：
```sql
select * from blog where 1 = 1 and title like ?
```
这就是`if标签`的功能，但是每次都要在`where`后面写`1=1`显得过于不符合规范，所以有了`trim`、`where`、`set`标签
# 三、trim、where、set

## 1、where
使用`where`标签可以不用加上面提到的例子中用到的`1 = 1`，使用`where`标签很简单，只需要嵌套在`if`标签外面就好了：
```java
<where>
	<if test="条件">
		value_one = #{value_one}
	</if>
	<if test="条件">
		and value_two = #{value_two}
	</if>
</where>
```
如果两个条件都满足，则`where`会自动拼接，如果第一个条件不满足第二个天条件满足，那么`where`标签会把第二个`if`中的`sql`前面的`and`自动去掉。

还是上面的需求，这次使用`where`，直接上`BlogMapper.xml`：
```xml
<select id="queryBlogByWhere" resultType="Blog" parameterType="map">
    select * from blog
    <where>
        <if test="author != null">
            author like #{author}
        </if>
        <if test="title != null">
            and title like #{title}
        </if>
    </where>
</select>
```
编写测试类：
```java
@Test
public void queryBlogByWhereTest(){
    SqlSession sqlSession = MyBatisUtils.getSqlSession();
    BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

    Map map = new HashMap();
    map.put("author","%狂%");
    map.put("title","%a%");
    List<Blog> blogs = mapper.queryBlogByWhere(map);
    for (Blog blog : blogs) {
        System.out.println(blog);
    }

    sqlSession.close();
}
```

两个条件都满足时：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210127160510771.png)
只有第二个条件满足时:
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210127160423131.png)
## 2、set
`set`标签只适用于更新语句，`set`标签会动态地在行首插入`SET`关键字，并会删掉额外的逗号

为方便起见，把表中的`id`改为`1234`：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210127160748641.png)
**需求：更新`id=3`后面的其他字段**

接口：
```java
int updateBlogBySet(Blog blog);
```
编写`BlogMapper.xml`：
```xml
<update id="updateBlogBySet" parameterType="Blog">
    update blog
    <set>
        <if test="title != null">title = #{title},</if>
        <if test="author != null">title = #{title},</if>
        <if test="create_time != null">create_time = #{create_time},</if>
        <if test="views != null">views = #{views}</if>
    </set>
    where id = #{id}
</update>
```
测试类：
```java
@Test
public void updateBlogBySetTest(){
    SqlSession sqlSession = MyBatisUtils.getSqlSession();
    BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

    Blog blog = new Blog();
    blog.setTitle("C++");
    blog.setViews(1000);
    blog.setId("3");
    mapper.updateBlogBySet(blog);

    //工具类中已经设置自动提交事务，所以这里不用再次提交事务
    sqlSession.close();
}
```
在测试类中只设置了`title`、`views`和`id`字段，运行一下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210127161708464.png)
可以看到`set`标签自动添加了`SET`关键字而且还删除了多余的逗号，看一下表：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210127161757519.png)
修改成功
## 3、trim
`trim`标签是`MyBatis`提供的让用户自定义标签的标签，这句话有点拗口，他有三个很重要的属性，分别是：

- `prefix`：要插入在最前面的值
- `prefixOverrides`：删除或添加前缀
- `suffixOverrides`：删除或添加后缀

比如`where`标签，用`trim`可以这样写：

```xml
<trim prefix="WHERE" prefixOverrides="AND |OR ">
  ...
</trim>
```
`set`标签可以这样写：
```xml
<trim prefix="SET" suffixOverrides=",">
  ...
</trim>
```
# 四、choose、when、otherwise
这三个标签类似于`java`的`swich`、`case`和`default`，在多个条件中只能选取一个！

**需求：传入了title就按title查，传入了author就按author查，否则按照views查**

接口：
```java
List<Blog> queryBlogByChoose(Map map);
```
xml：
```xml
<select id="queryBlogByChoose" parameterType="map" resultType="Blog">
    select * from Blog 
    <where>
        <choose>
            <when test="title != null">
                and title like #{title}
            </when>
            <when test="author != null">
                and author like #{author}
            </when>
            <otherwise>
                and views > 5000
            </otherwise>
        </choose>
    </where>
</select>
```
测试类：
```java
@Test
public void queryBlogByChooseTest(){
    SqlSession sqlSession = MyBatisUtils.getSqlSession();
    BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

    Map map = new HashMap();
    map.put("title","%J%");

    List<Blog> blogs = mapper.queryBlogByChoose(map);
    for (Blog blog : blogs) {
        System.out.println(blog);
    }

    sqlSession.close();
}
```
结果：
![在这里插入图片描述](https://img-blog.csdnimg.cn/2021012716355197.png)
# 五、foreach
`foreach`就是循环喽，他有六个属性：

- `item`：集合项，类似于数组的下标，值
- `index`：索引，键
- `collection`：传入的集合
- `open`：以什么作为开头的字符串
- `separator`：分隔符
- `close`：以什么作为结尾的字符串

下面是一个简单的，不使用`limit`的查询第2、3、4项的`sql`：
```xml
<select id="queryBlogByForeach" resultType="Blog" parameterType="map">
    select * from blog where id in 
    <!-- 这个ids是map传入的集合 -->
    <foreach collection="ids" item="item" index="index"
             open="(" close=")" separator=",">
        #{item}
    </foreach>
</select>
```
