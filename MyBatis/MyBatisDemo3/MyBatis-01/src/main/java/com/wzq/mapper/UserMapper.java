package com.wzq.mapper;

import com.wzq.domain.User;

import java.util.List;

public interface UserMapper {

    //查询所有用户
    List<User> findAll();

    //根据ID查询用户
    User findUserById(int id);

    //插入一个用户
    void addUser(User user);

    //修改用户
    void updateUser(User user);

    //删除用户
    void deleteUser(int id);

}
