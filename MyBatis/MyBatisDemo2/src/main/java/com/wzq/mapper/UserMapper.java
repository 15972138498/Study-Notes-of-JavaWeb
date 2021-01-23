package com.wzq.mapper;

import com.wzq.domain.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    List<User> findAllUser();

    User findUserById(int id);

    List<User> findUserByLimit(Map<String,Integer> map);

    List<User> findUserByRowBounds();

}
