package com.wzq.dao.impl;

import com.wzq.dao.UserDao;
import com.wzq.domain.User;
import com.wzq.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<User> findAll() {
        //使用JDBC操作数据库
        String sql = "select * from USER";
        List<User> users = template.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return users;
    }

    //登陆
    @Override
    public User loginByUserNameAndPassword(User loginUser) {
        try {
            String sql = "select * from USER where username = ? and password = ?";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),
                    loginUser.getUsername(), loginUser.getPassword());
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void add(User user) {
        String sql = "insert into USER values(null,?,?,?,?,?,?,null,null)";
        template.update(sql, user.getName(), user.getGender(), user.getAge(), user.getAddress(), user.getQq(), user.getEmail());
    }

    @Override
    public void delete(int id) {
        String sql = "delete from USER where id = ?";
        template.update(sql, id);
    }

    @Override
    public User findById(int id) {
        String sql = "select * from USER where id = ?";
        User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
        return user;
    }

    @Override
    public void update(User user) {
        String sql = "update USER set NAME = ? , gender = ? , age = ? , address = ? , qq = ? , email = ? where id = ?";
        template.update(sql,
                user.getName(), user.getGender(), user.getAge(),
                user.getAddress(), user.getQq(), user.getEmail(), user.getId());
    }

    @Override
    public int findTotalCount(Map<String, String[]> condition) {
        //定义模板sql
        String sql = "select count(id) from USER where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);

        //遍历map
        Set<String> keySet = condition.keySet();
        List<Object> params = new ArrayList<Object>();
        for (String key : keySet) {
            if("currentPage".equals(key)||"rows".equals(key)){
                continue;
            }
            //获取value
            String value = condition.get(key)[0];

            if (value != null && !"".equals(value)) {
                //有值
                sb.append(" and " + key + " like ? ");
                params.add("%" + value + "%");
            }
        }

        System.out.println(sb);
        System.out.println(params);

        return template.queryForObject(sb.toString(), Integer.class,params.toArray());
    }

    @Override
    public List<User> findByPage(int start, int rows, Map<String, String[]> condition) {
        String sql = "select * from USER where 1 = 1 ";

        StringBuilder sb = new StringBuilder(sql);
        //遍历map
        Set<String> keySet = condition.keySet();
        List<Object> params = new ArrayList<Object>();
        for (String key : keySet) {
            if("currentPage".equals(key)||"rows".equals(key)){
                continue;
            }
            //获取value
            String value = condition.get(key)[0];

            if (value != null && !"".equals(value)) {
                //有值
                sb.append(" and " + key + " like ? ");
                params.add("%" + value + "%");
            }
        }

        sb.append(" limit ?,?");
        //添加分页查询参数值
        params.add(start);
        params.add(rows);

        System.out.println(sb);
        System.out.println(params);

        List<User> list = template.query(sb.toString(), new BeanPropertyRowMapper<User>(User.class),params.toArray());
        return list;
    }
}
