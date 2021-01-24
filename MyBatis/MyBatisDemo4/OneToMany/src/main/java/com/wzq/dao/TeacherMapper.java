package com.wzq.dao;

import com.wzq.pojo.Teacher;
import org.apache.ibatis.annotations.Param;

public interface TeacherMapper {

    //找到指定老师对应的学生
    Teacher getTeacherById1(@Param("tid") int id);

    Teacher getTeacherById2(@Param("tid") int id);

}
