package com.wzq.web.servlet;

import com.wzq.domain.PageBean;
import com.wzq.domain.User;
import com.wzq.service.UserService;
import com.wzq.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/findUserByPageServlet")
public class FindUserByPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收请求参数
        String currentPage = request.getParameter("currentPage");
        String rows = request.getParameter("rows");
        if (currentPage == null || "".equals(currentPage)) {
            currentPage = "1";

        }
        if (rows == null || "".equals(rows)) {
            rows = "5";
        }

        //获取参数
        Map<String, String[]> condition = request.getParameterMap();

        //调用Service查询PageBean
        UserService service = new UserServiceImpl();
        PageBean<User> page = service.findUserByPage(currentPage, rows, condition);

        System.out.println(page);

        //将PageBean存入request
        request.setAttribute("page", page);
        //将查询条件也存入request
        request.setAttribute("condition",condition);
        //转到到list.jsp
        request.getRequestDispatcher("/list.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
