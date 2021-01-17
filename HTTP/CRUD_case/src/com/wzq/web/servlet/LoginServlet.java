package com.wzq.web.servlet;

import com.wzq.domain.User;
import com.wzq.service.UserService;
import com.wzq.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //设置编码
        request.setCharacterEncoding("utf-8");
        //获取验证码
        String verifycode = request.getParameter("verifycode");
        //拿到系统生成的验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");

        if(!checkcode_server.equalsIgnoreCase(verifycode)){
            //登陆失败
            //设置request域显示失败信息
            request.setAttribute("login_error","验证码错误！");
            //转发到login.jsp
            request.getRequestDispatcher("/login.jsp").forward(request,response);
            return;
        }

        //获取其他数据，并封装到User实体类
        Map<String, String[]> map = request.getParameterMap();
        User loginUser = new User();
        try {
            BeanUtils.populate(loginUser,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //调用service层拿到真正的user
        UserService service = new UserServiceImpl();
        User user = service.loginByUserNameAndPassword(loginUser);
        if(user!=null){
            //如果user非空，登陆成功
            //传一个User，到index.jsp页面
            session.setAttribute("user",loginUser);
            response.sendRedirect(request.getContextPath()+"/index.jsp");
        }else{
            //失败
            //存储信息
            request.setAttribute("login_error","用户名或密码错误！");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
