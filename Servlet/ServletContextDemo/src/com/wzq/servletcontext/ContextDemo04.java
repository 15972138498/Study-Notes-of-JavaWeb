package com.wzq.servletcontext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/contextDemo04")
public class ContextDemo04 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 获取ServletContext对象
        ServletContext context = this.getServletContext();
        //2. 获取web目录下的资源访问，b.txt
        String bpath = context.getRealPath("/b.txt");
        System.out.println("b.txt路径：" + bpath);
        //3. 获取WEB-INF目录下的资源访问 c.txt
        String cpath = context.getRealPath("/WEB-INF/c.txt");
        System.out.println("c.txt路径：" + cpath);
        //4. 获取src目录下的资源访问 a.txt
        String apath = context.getRealPath("/WEB-INF/classes/a.txt");
        System.out.println("a.txt路径：" + apath);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
