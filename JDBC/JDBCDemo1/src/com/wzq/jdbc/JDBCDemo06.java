package com.wzq.jdbc;

import com.wzq.util.JDBCUtils;

import java.sql.*;
import java.util.Scanner;

/*
 *   需求：
 *       1、通过键盘录入用户名和密码
 *       2、判断用户是否登陆成功
 * */
public class JDBCDemo06 {

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String username = cin.nextLine();
        System.out.println("请输入密码：");
        String password = cin.nextLine();

        boolean res  = new JDBCDemo06().login(username, password);
        if (res) System.out.println("登陆成功！");
        else System.out.println("登陆失败！");
        System.out.println("---------------------------------------------------");
    }

    public boolean login(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            //1、获取数据库连接
            conn = JDBCUtils.getConnection();   //JDBCUtils类
            //2、定义sql
            String sql = "select * from user where username = ? and password = ?";

            //3、获取执行sql的对象
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,username);
            pstmt.setString(2,password);
            //4、执行sql
            rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, pstmt, conn);
        }
        return false;
    }
}
