@[toc]


-----
# 〇、需求
**需求：找到指定老师对应的学生**
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

然后创建两张表对应的实体类：

`Teacher`表对应的实体类：
```java
package com.wzq.pojo;

import java.util.List;

public class Teacher {
    private int id;
    private String name;
    private List<Student> students;

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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", students=" + students +
                '}';
    }
}
```
`Student`表对应的实体类：
```java
package com.wzq.pojo;

public class Student {
    private int id;
    private String name;
    private int tid;

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

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tid=" + tid +
                '}';
    }
}
```
# 二、按照结果嵌套查询
首先在`TeacherMapper`中写方法：
```java
Teacher getTeacherById1(@Param("tid") int id);
```
然后在`TeacherMapper.xml`中写`sql`：
```xml
    <select id="getTeacherById1" resultMap="Teacher_Student1">
        select
            t.id tid,
            t.name tname,
            s.id sid,
            s.name sname
        from
            teacher t,
            student s
        where
            t.id = s.tid and t.id = #{tid};
    </select>
    <resultMap id="Teacher_Student1" type="Teacher">
        <id property="id" column="tid" />
        <result property="name" column="tname" />
        <collection property="students" ofType="Student">
            <id property="id" column="sid"/>
            <result property="name" column="sname"/>
            <result property="tid" column="tid"/>
        </collection>
    </resultMap>
```
最后测试：
```java
    @Test
    public void getTeacherById1Test(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        Teacher teacher = mapper.getTeacherById1(1);
        System.out.println(teacher);
        sqlSession.close();
    }
```
结果：
```java
[com.wzq.dao.TeacherMapper.getTeacherById1]-==>  Preparing: select t.id tid, t.name tname, s.id sid, s.name sname from teacher t, student s where t.id = s.tid and t.id = ?;
[com.wzq.dao.TeacherMapper.getTeacherById1]-==> Parameters: 1(Integer)
[com.wzq.dao.TeacherMapper.getTeacherById1]-<==      Total: 5
Teacher{id=1, name='刘老师', students=[Student{id=1, name='小明', tid=1}, Student{id=2, name='小红', tid=1}, Student{id=3, name='小张', tid=1}, Student{id=4, name='小李', tid=1}, Student{id=5, name='小王', tid=1}]}
```
# 三、按照查询嵌套处理
同上面的步骤，写方法：
```java
Teacher getTeacherById2(@Param("tid") int id);
```
然后在`TeacherMapper.xml`中写`sql`：
```xml
    <select id="getTeacherById2" resultMap="Teacher_Student2">
        select * from teacher where id = #{tid};
    </select>
    <resultMap id="Teacher_Student2" type="Teacher">
        <id property="id" column="id"/>
        <result property="name" column="name"/>
        <collection property="students" javaType="ArrayList" ofType="Students" select="getStudentByTeacherId" column="id"/>
    </resultMap>
    <select id="getStudentByTeacherId" resultType="Student">
        select * from student where tid = #{tid};
    </select>
```
最后测试：
```java
    @Test
    public void getTeacherById2Test(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        Teacher teacher = mapper.getTeacherById2(1);
        System.out.println(teacher);
        sqlSession.close();
    }
```
