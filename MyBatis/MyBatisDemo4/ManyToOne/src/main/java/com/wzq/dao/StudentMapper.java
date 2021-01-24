package com.wzq.dao;

import com.wzq.pojo.Student;

import java.util.List;

public interface StudentMapper {

    //查询所有学生信息，以及对应老师的信息
    List<Student> getStudent1();

    List<Student> getStudent2();
}
