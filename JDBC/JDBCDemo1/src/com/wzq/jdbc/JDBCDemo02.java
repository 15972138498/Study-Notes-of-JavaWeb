package com.wzq.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCDemo02 {

    public static void main(String[] args) {

        Connection conn = null;
        Statement stmt = null;

        try {
            //1、导入驱动jar包

            //2、注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //3、获取数据库链接对象Connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_test?serverTimezone=GMT", "root", "wzq99121%%");
            //4、定义sql语句
            String sql = "insert into account values(null,'wangwu',2000)";
            //5、获取执行sql的对象Statement
            stmt = conn.createStatement();
            //6、执行sql
            int cnt = stmt.executeUpdate(sql);
            //7、判断接受结果
            if (cnt > 0) {
                System.out.println("创建成功！");
            } else {
                System.out.println("创建失败！");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
