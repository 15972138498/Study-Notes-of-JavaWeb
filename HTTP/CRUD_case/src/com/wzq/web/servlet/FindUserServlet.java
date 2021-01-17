package com.wzq.web.servlet;

import com.wzq.domain.User;
import com.wzq.service.UserService;
import com.wzq.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/findUserServlet")
public class FindUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置request编码
        request.setCharacterEncoding("utf-8");
        //获取id
        String id = request.getParameter("id");
        //传递到service层
        UserService service = new UserServiceImpl();
        User user = service.findById(id);
        //存储user
        request.setAttribute("user",user);
        //转发到update.jsp
        request.getRequestDispatcher("/update.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
