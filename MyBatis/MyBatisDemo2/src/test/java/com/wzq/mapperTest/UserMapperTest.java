package com.wzq.mapperTest;

import com.wzq.domain.User;
import com.wzq.mapper.UserMapper;
import com.wzq.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.List;

public class UserMapperTest {

    //获取SqlSession
    private SqlSession sqlSession = MyBatisUtils.getSqlSession();
    //获取mapper
    private UserMapper mapper = sqlSession.getMapper(UserMapper.class);

    //测试找到所有用户
    @Test
    public void findAllUserTest(){
        List<User> allUser = mapper.findAllUser();
        for (User user : allUser) {
            System.out.println(user);
        }
        sqlSession.close();
    }


    @Test
    public void findUserByIdTest(){
        User user = mapper.findUserById(5);
        System.out.println(user);
        sqlSession.close();
    }

    @Test
    public void log4jTest(){
        Logger logger = Logger.getLogger(UserMapperTest.class);
        logger.info("info：进入log4jTest了...");
        logger.debug("debug：进入log4jTest了...");
        logger.error("error：进入log4jTest了...");
    }

}
