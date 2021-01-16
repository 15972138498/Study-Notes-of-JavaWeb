<%@ page import="java.net.URLDecoder" %><%--
  Created by IntelliJ IDEA.
  User: wzq
  Date: 2021/1/16
  Time: 15:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>success</title>
</head>
<body>
<h1><%=request.getSession().getAttribute("user")%>,欢迎您</h1>
<br>
<%
    boolean flag = (boolean) request.getSession().getAttribute("flag");
    if (flag == false) {
        //首次登陆
        out.write("这是您首次登陆！");
    } else {
        //不是首次
        String date = (String) request.getSession().getAttribute("date");
        date = URLDecoder.decode(date,"utf-8"); //解码
        out.write("您上次登陆的时间为：" + date);
    }
%>
</body>
</html>
