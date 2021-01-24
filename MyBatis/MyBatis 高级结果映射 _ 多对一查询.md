@[toc]


----
# 〇、需求
**需求：查询所有学生信息，以及对应老师的信息**
# 一、搭建环境
多对一就比如多个学生对应一个老师，多对一是基于多表查询的，首先搭建`MySQL`环境：
```sql
CREATE DATABASE MyBatis_DB;
USE MyBatis_DB;
CREATE TABLE `teacher` (
  `id` INT(10) NOT NULL,
  `name` VARCHAR(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

INSERT INTO teacher(`id`, `name`) VALUES (1, '刘老师'); 

CREATE TABLE `student` (
  `id` INT(10) NOT NULL,
  `name` VARCHAR(30) DEFAULT NULL,
  `tid` INT(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fktid` (`tid`),
  CONSTRAINT `fktid` FOREIGN KEY (`tid`) REFERENCES `teacher` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;
INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('1', '小明', '1'); 
INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('2', '小红', '1'); 
INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('3', '小张', '1'); 
INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('4', '小李', '1'); 
INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('5', '小王', '1');
```
建表结果如下图所示：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210124192347371.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
打开`idea`，新建`maven`项目，然后在`pom.xml`文件中注入`mysql`、`MyBatis`、`Junit`、`LOG4J`依赖

然后创建两个表的实体类：

学生表实体类：
```java
package com.wzq.pojo;

public class Student {

    private int id;
    private String name;
    private Teacher teacher;

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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teacher=" + teacher +
                '}';
    }
}
```
老师表实体类：
```java
package com.wzq.pojo;

public class Teacher {

    private int id;
    private String name;

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

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

```
然后创建`StudentMapper`、`TeacherMapper`接口以及他们对应的`xml`文件
# 二、按照结果嵌套查询
按照结果嵌套查询，首先在`StudentMapper`接口中，写下方法：
```java
List<Student> getStudent2();
```
然后在`StudentMapper.xml`中写代码：
```xml
    <select id="getStudent2" resultMap="Student_Teacher2">
        select
            s.id sid,
            s.name sname,
            t.name tname
        from
            student s,
            teacher t
        where
            s.tid = tid;
    </select>
    <resultMap id="Student_Teacher2" type="Student">
    	<!--
    	 	property：实体类中的属性 
    	 	column：表的列名
    	-->
        <id property="id" column="sid" />
        <result property="name" column="sname"/>
        <association property="teacher" javaType="Teacher">
            <result property="name" column="tname"/>
        </association>
    </resultMap>
```
最后测试：
```java
    @Test
    public void getStudent1Test(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> studentList = mapper.getStudent2();
        for (Student student : studentList) {
            System.out.println(student);
        }
        sqlSession.close();
    }
```
输出结果：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210124192938930.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
# 三、按照查询嵌套处理
首先在`StudentMapper`中写下方法：
```java
List<Student> getStudent1();
```
然后在`StudentMapper.xml`中写`sql`与结果映射：

```xml
    <select id="getStudent1" resultMap="Student_Teacher1">
        select * from student;
    </select>
    <resultMap id="Student_Teacher1" type="Student">
        <id property="id" column="id"/>
        <result property="name" column="name"/>
        <association property="teacher" column="tid" javaType="Teacher" select="getTeacher" />
    </resultMap>
    <select id="getTeacher" resultType="Teacher">
        select * from teacher where id = #{tid};
    </select>
```
最后测试：
```java
    @Test
    public void getStudent1Test(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> studentList = mapper.getStudent2();
        for (Student student : studentList) {
            System.out.println(student);
        }
        sqlSession.close();
    }
```
结果：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210124192938930.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
