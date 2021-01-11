package com.wzq.dao;

import com.wzq.domain.User;
import com.wzq.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/*
 * 操作数据库中User表的类
 * */
public class UserDao {

    //声明JDBCTemplaet对象共有
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    //登陆方法
    public User login(User loginUser) {
        try {
            //1.编写sql
            String sql = "select * from user where username = ? and password = ?";
            //2.调用方法
            User user = template.queryForObject(sql,
                    new BeanPropertyRowMapper<User>(User.class),
                    loginUser.getUsername(), loginUser.getPassword());
            return user;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
