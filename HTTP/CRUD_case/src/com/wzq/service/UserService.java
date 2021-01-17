package com.wzq.service;

import com.wzq.domain.User;

import java.util.List;

/*
 * 用户管理的业务接口
 * */
public interface UserService {

    //展示用户所有数据
    List<User> findAll();
    //登陆
    User loginByUserNameAndPassword(User loginUser);
    //添加人员
    void add(User user);
    //通过id删除用户
    void delete(String id);

    User findById(String id);

    void update(User user);
}
