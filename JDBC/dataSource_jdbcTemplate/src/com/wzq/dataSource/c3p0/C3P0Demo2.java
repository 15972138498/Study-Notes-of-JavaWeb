package com.wzq.dataSource.c3p0;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/*
 * C3P0演示
 * */
public class C3P0Demo2 {

    public static void main(String[] args) throws SQLException {
        //1. 获取DataSource
        DataSource ds = new ComboPooledDataSource();
        //2. 获取执行对象
        for (int i = 0; i < 10; i++) {
            Connection conn = ds.getConnection();
            System.out.println(i + " : " + conn);
            conn.close();       //归还资源
        }



    }

}
