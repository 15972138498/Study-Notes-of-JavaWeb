package com.wzq.service.impl;

import com.wzq.dao.UserDao;
import com.wzq.dao.impl.UserDaoImpl;
import com.wzq.domain.User;
import com.wzq.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao dao = new UserDaoImpl();

    //查询所有用户
    @Override
    public List<User> findAll() {
        //调用dao层findAll
        return dao.findAll();
    }

    @Override
    public User loginByUserNameAndPassword(User loginUser) {
        return dao.loginByUserNameAndPassword(loginUser);
    }

    @Override
    public void add(User user) {
        //调用dao层方法
        dao.add(user);
    }

    @Override
    public void delete(String id) {
        //调用dao层删除方法
        dao.delete(Integer.parseInt(id));
    }

    @Override
    public User findById(String id) {
        return dao.findById(Integer.parseInt(id));
    }

    @Override
    public void update(User user) {
        dao.update(user);
    }


}
