package com.wzq.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class JDBCDemo01 {

    public static void main(String[] args) throws Exception{

        //1、导入驱动jar包

        //2、注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //3、获取数据库链接对象
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_test?serverTimezone=GMT", "root", "wzq99121%%");
        //4、定义sql
        String sql = "update account set balance = 1500 where id = 1";
        //5、获取执行sql语句的对象Connection
        Statement stmt = conn.createStatement();
        //6、执行sql，接受返回结果
        int cnt = stmt.executeUpdate(sql);
        //7、处理结果
        if(cnt>0){
            System.out.println("执行成功！");
        }else{
            System.out.println("执行失败！");
        }
        //8、释放资源
        stmt.close();
        conn.close();

    }
}
