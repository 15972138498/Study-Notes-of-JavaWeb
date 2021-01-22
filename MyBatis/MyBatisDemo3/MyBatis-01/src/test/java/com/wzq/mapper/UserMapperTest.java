package com.wzq.mapper;

import com.wzq.domain.User;
import com.wzq.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class UserMapperTest {

    //抽取属性，获取SqlSession对象
    private SqlSession sqlSession = MyBatisUtils.getSqlSession();
    //抽取属性，获取mapper
    private UserMapper mapper = sqlSession.getMapper(UserMapper.class);

    //测试查询所有用户
    @Test
    public void findAllTest(){
        //2. 执行
        List<User> all = mapper.findAll();
        for (User user : all) {
            System.out.println(user);
        }
        //3. 关闭连接
        sqlSession.close();
    }

    //测试根据ID查询用户
    @Test
    public void findUserByIdTest(){
        //2. 执行
        User user = mapper.findUserById(1);
        System.out.println(user);
        //3. 关闭连接
        sqlSession.close();
    }

    //增删改需要提交事务
    //插入一个用户
    @Test
    public void addUserTest(){
        User user = new User(null,"大王","1234215");
        mapper.addUser(user);
        //提交事务
        sqlSession.commit();
        findAllTest();
    }

    //修改用户
    @Test
    public void updateUserTest(){
        mapper.updateUser(new User(1,"王五","123456"));
        sqlSession.commit();
        sqlSession.close();
    }

    //删除用户
    @Test
    public void deleteUserTest(){
        mapper.deleteUser(1);
        sqlSession.commit();
        sqlSession.close();
    }



}
