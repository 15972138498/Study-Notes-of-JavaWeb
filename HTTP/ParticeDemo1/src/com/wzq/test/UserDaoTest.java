package com.wzq.test;

import com.wzq.dao.UserDao;
import com.wzq.domain.User;
import org.junit.Test;

public class UserDaoTest {

    //测试用户登陆的方法
    @Test
    public void LoginTest(){
        User loginUser = new User();
        loginUser.setUsername("superbaby");
        loginUser.setPassword("213123");

        UserDao ud = new UserDao();
        User user = ud.Login(loginUser);
        System.out.println(user);
    }

}
