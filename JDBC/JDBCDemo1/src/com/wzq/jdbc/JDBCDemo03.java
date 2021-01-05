package com.wzq.jdbc;

import java.sql.*;

public class JDBCDemo03 {

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            //1、注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2、获取数据库链接对象Connection
            conn = DriverManager.getConnection("jdbc:mysql:///db_test?serverTimezone=GMT", "root", "wzq99121%%");
            //3、定义sql
            String sql = "select * from account";
            //4、获取执行sql的对象statement
            stmt = conn.createStatement();
            //5、执行sql
            rs = stmt.executeQuery(sql);
            //6、遍历ResultSet
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " " + rs.getString("name") + " " + rs.getDouble("balance"));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //7、释放链接
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
