package com.wzq.dataSource.druid;

/*
* 演示Druid
* */

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class DruidDemo {
    public static void main(String[] args) throws Exception {
        //3.加载配置文件
        Properties pro = new Properties();
        InputStream id = DruidDemo.class.getClassLoader().getResourceAsStream("druid.properties");
        pro.load(id);
        //4.获取连接池对象
        DataSource ds = DruidDataSourceFactory.createDataSource(pro);
        //5.获取连接
        Connection conn = ds.getConnection();
        System.out.println(conn);
    }
}
