<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.net.URLEncoder" %><%--
  Created by IntelliJ IDEA.
  User: wzq
  Date: 2021/1/13
  Time: 19:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>显示时间</title>
</head>
<body>

<%
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
%>
<h1>欢迎回来，您上次的访问时间为：<%=value%>
</h1>
<%
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
        Cookie cookie = new Cookie("lastTime", str_date);
        cookie.setMaxAge(60 * 60);
        response.addCookie(cookie);
%>
<h1>您好，欢迎您首次访问</h1>
<%
    }
%>

</body>
</html>
