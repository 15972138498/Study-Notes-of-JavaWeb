package com.wzq.daoTest;

import com.wzq.dao.UserMapper;
import com.wzq.pojo.User;
import com.wzq.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class UserMapperTest {
    @Test
    public void findUserByIdTest(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User user1 = mapper.findUserById(5);
        System.out.println(user1);
        System.out.println("===============================================================");
        User user2 = mapper.findUserById(6);
        System.out.println(user2);

        sqlSession.close();
    }
}
