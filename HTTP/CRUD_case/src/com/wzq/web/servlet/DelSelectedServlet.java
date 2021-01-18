package com.wzq.web.servlet;

import com.wzq.service.UserService;
import com.wzq.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//批量删除用户
@WebServlet("/delSelectedServlet")
public class DelSelectedServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取所有id
        String[] ids = request.getParameterValues("uid");
        //调用service
        UserService service = new UserServiceImpl();
        service.delSelectedUser(ids);
        //重定向到UserListServlet
        response.sendRedirect(request.getContextPath()+"/findUserByPageServlet");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
