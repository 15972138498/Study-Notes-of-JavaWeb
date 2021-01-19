package com.wzq.mapper;

import com.wzq.domain.User;
import com.wzq.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class UserMapperTest {
    @Test
    public void findAllTest(){

        //1. 获取SqlSession
        SqlSession sqlSession = MyBatisUtils.getSqlSession();

        //2. 执行
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> all = mapper.findAll();
        for (User user : all) {
            System.out.println(user);
        }

        //3. 关闭连接
        sqlSession.close();

    }
}
