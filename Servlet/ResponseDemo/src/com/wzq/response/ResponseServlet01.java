package com.wzq.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/responseServlet01")
public class ResponseServlet01 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //打印一句话
        System.out.println("responseServlet01被访问了...");
    /*
        //1. 设置状态码为302
        response.setStatus(302);
        //2. 设置响应头location
        response.setHeader("location","/ResponseDemo/responseServlet02");

     */

        response.sendRedirect("/ResponseDemo/responseServlet02");


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
