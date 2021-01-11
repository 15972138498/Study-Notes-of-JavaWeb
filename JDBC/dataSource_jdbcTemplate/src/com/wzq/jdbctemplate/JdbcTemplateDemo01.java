package com.wzq.jdbctemplate;

import com.wzq.utils.JDBCUtils;
import org.springframework.jdbc.core.JdbcTemplate;
//jdbcTemplate简单演示
public class JdbcTemplateDemo01 {
    public static void main(String[] args) {
        //2.创建JdbcTemplate对象
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
        //3.调用方法
        String sql = "update account set balance = 5000 where id = ?";
        int cnt = template.update(sql, 3);
        System.out.println(cnt);
    }
}
