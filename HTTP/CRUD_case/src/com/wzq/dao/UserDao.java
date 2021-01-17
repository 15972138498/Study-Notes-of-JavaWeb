package com.wzq.dao;

import com.wzq.domain.User;

import java.util.List;

/*
* 用户Dao
* */
public interface UserDao {

    List<User> findAll();

    User loginByUserNameAndPassword(User loginUser);

    void add(User user);

    void delete(int id);

    User findById(int id);

    void update(User user);
}
