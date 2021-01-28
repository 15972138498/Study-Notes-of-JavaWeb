package com.wzq.dao;

import com.wzq.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    User findUserById(@Param("id") int id);

}
