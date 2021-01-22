package com.wzq.mapper;

import com.wzq.domain.User;

import java.util.List;

public interface UserMapper {

    List<User> findAllUser();

    User findUserById(int id);

}
