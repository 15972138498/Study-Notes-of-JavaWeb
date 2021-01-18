package com.wzq.dao;

import com.wzq.domain.User;

import java.util.List;
import java.util.Map;

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

    int findTotalCount(Map<String, String[]> condition);

    List<User> findByPage(int start, int rows, Map<String, String[]> condition);
}
