package com.wzq.test;

import com.wzq.dao.UserDao;
import com.wzq.domain.User;
import org.junit.Test;

public class UserDaoTest {

    //测试用户登陆
    @Test
    public void testLogin(){
        User user = new User();
        user.setUsername("superbaby");
        user.setPassword("123");
        UserDao dao = new UserDao();
        User loginuser = dao.login(user);
        System.out.println(loginuser);
    }

}
