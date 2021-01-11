package com.wzq.jdbctemplate;

import com.wzq.domain.Account;
import com.wzq.utils.JDBCUtils;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static javafx.scene.input.KeyCode.T;

public class JdbcTemplateDemo02 {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /*
     * 1. 修改1号的balance为4500
     * */
    @Test
    public void test1() {
        String sql = "update account set balance = ? where id = ?";
        int cnt = template.update(sql,4500, 1);
        System.out.println(cnt);
    }

    /*
    * 2. 添加一条记录
    * */
    @Test
    public void test2(){
        String sql = "insert into account values(null,?,?)";
        int cnt = template.update(sql,"wangsan",100);
        System.out.println(cnt);
    }

    /*
    * 3. 删除id=5的记录
    * */
    @Test
    public void test3(){
        String sql = "delete from account where id = ?";
        int cnt = template.update(sql, 5);
        System.out.println(cnt);
    }

    /*
    * 4. 查询 id = 1 的记录，将其封装为Map集合
    * */
    @Test
    public void test4(){
        String sql = "select * from account where id = ?";
        Map<String, Object> stringObjectMap = template.queryForMap(sql, 1);
        System.out.println(stringObjectMap);
    }

    /*
    * 5. 查询所有记录，将其封装为 List
    * */
    @Test
    public void test5(){
        String sql = "select * from account";
        List<Map<String, Object>> list = template.queryForList(sql);
        for(Map<String,Object> map : list){
            System.out.println(map);
        }
    }

    /*
    * 6. 查询所有记录，将其封装为 Account 对象的 List 集合
    * */
    @Test
    public void test6_1(){
        String sql = "select * from account";
        List<Account> list = template.query(sql, new RowMapper<Account>() {
            @Override
            public Account mapRow(ResultSet rs, int i) throws SQLException {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double balance = rs.getDouble("balance");
                Account account = new Account();
                account.setId(id);
                account.setName(name);
                account.setBalance(balance);
                return account;
            }
        });
        for(Account account : list){
            System.out.println(account);
        }
    }

    /*
     * 6. 查询所有记录，将其封装为 Account 对象的 List 集合
     * */
    @Test
    public void test6_2(){
        String sql = "select * from account";
        List<Account> list = template.query(sql, new BeanPropertyRowMapper<Account>(Account.class));
        for(Account account : list){
            System.out.println(account);
        }
    }

    /*
    * 7. 查询表中的总记录数
    * */
    @Test
    public void test7(){
        String sql = "select count(id) from account";
        Long total = template.queryForObject(sql, Long.class);
        System.out.println(total);
    }
}
