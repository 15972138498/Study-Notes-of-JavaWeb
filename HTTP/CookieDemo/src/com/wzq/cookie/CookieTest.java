package com.wzq.cookie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
    案例：记住上一次访问时间
    1. 需求
        1. 访问一个Servlet，如果是第一次访问，则提示：您好，欢迎您首次访问
        2. 如果不是第一次访问，则提示：欢迎回来，您上次的访问时间为：显示时间字符串
    2. 分析：
        1. 可以采用Cookie来完成
        2. 在服务器中的Servlet判断是否有一个名为lastTime的Cookie
            1. 有：不是第一次访问
                1. 响应数据：欢迎回来，您上次的访问时间为：显示时间字符串
                2. 写回Cookie：lastTime=时间
            2. 没有：是第一次访问
                1. 响应数据：您好，欢迎您首次访问
                2. 写回Cookie：lastTime=时间

* */

@WebServlet("/cookieTest")
public class CookieTest extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置响应编码
        response.setContentType("text/html;charset=utf-8");
        //接收cookie
        Cookie[] cookies = request.getCookies();
        //遍历cookie数组
        boolean flag = false;
        if (cookies.length > 0) {
            for (Cookie cookie : cookies) {
                //判断是否等于lastTime
                String name = cookie.getName();
                if ("lastTime".equals(name)) {
                    flag = true;    //如果有lastTime则把flag设为true
                    //响应数据
                    String value = cookie.getValue();   //获取cookie存储的时间
                    value = URLDecoder.decode(value, "utf-8");  //value解码
                    response.getWriter().write("<h1>欢迎回来，您上次的访问时间为：" + value + "</h1>");

                    //把当前时间写回cookie
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                    String str_date = sdf.format(date);
                    str_date = URLEncoder.encode(str_date, "utf-8");    //编码
                    cookie.setValue(str_date);  //给cookie重新赋值
                    cookie.setMaxAge(60 * 60);  //设置最大存活时间
                    response.addCookie(cookie); //发送cookie
                    break;
                }
            }
        }
        if (cookies.length == 0 || !flag) {
            //第一次访问
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            String str_date = sdf.format(date);
            str_date = URLEncoder.encode(str_date, "utf-8");
            Cookie cookie = new Cookie("lastTime",str_date);
            cookie.setMaxAge(60 * 60);
            response.addCookie(cookie);
            response.getWriter().write("<h1>您好，欢迎您首次访问</h1>");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
