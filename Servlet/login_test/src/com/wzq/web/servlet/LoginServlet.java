package com.wzq.web.servlet;

import com.wzq.dao.UserDao;
import com.wzq.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("utf-8");
        //2.获取请求参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //3.封装User对象
        User loginuser  = new User();
        loginuser.setUsername(username);
        loginuser.setPassword(password);
        //4.调用UserDao的login方法
        UserDao dao = new UserDao();
        User user = dao.login(loginuser);
        //5.判断user
        if(user==null){
            //失败
            request.getRequestDispatcher("/FailServlet").forward(request,response);
        }else{
            //成功
            //存储数据
            request.setAttribute("user",user);
            request.getRequestDispatcher("/SuccessServlet").forward(request,response);
        }
    }
}
