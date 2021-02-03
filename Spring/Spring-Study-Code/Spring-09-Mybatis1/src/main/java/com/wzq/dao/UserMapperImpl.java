package com.wzq.dao;

import com.wzq.pojo.User;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

public class UserMapperImpl extends SqlSessionDaoSupport implements UserMapper {

    public List<User> showAllUser() {
        return getSqlSession().getMapper(UserMapper.class).showAllUser();
    }
}
