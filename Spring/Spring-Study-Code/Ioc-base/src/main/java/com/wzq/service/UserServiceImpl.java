package com.wzq.service;

import com.wzq.dao.UserDao;
import com.wzq.dao.UserDaoImpl;
import com.wzq.dao.UserDaoMySqlImpl;
import com.wzq.dao.UserDaoOracleImpl;

public class UserServiceImpl implements UserService {

    private UserDao dao ;

    //利用set进行动态实现值的注入！
    public void setUserDao(UserDao dao) {
        this.dao = dao;
    }

    public void getUser() {
        dao.getUser();
    }
}
