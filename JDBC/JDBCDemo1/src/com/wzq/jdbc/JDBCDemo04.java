package com.wzq.jdbc;

import com.wzq.domain.account;
import com.wzq.util.JDBCUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCDemo04 {

    public static void main(String[] args) {
        List<account> l = findAll();
        System.out.println(l);
    }

    public static List<account> findAll(){
        List<account> list = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            conn = JDBCUtils.getConnection();
            //3、定义sql
            String sql = "select * from account";
            //4、获取执行sql语句的对象Statement
            stmt = conn.createStatement();
            //5、执行sql
            rs = stmt.executeQuery(sql);
            list = new ArrayList<account>();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double balance = rs.getDouble("balance");

                account a = new account();
                a.setId(id);
                a.setName(name);
                a.setBalance(balance);

                list.add(a);
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs,stmt,conn);
        }
        return list;
    }

}
