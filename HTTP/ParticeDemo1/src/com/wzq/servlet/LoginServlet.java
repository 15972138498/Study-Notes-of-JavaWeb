package com.wzq.servlet;

import com.wzq.dao.UserDao;
import com.wzq.domain.User;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/*

需求：
    - 访问带有验证码的登陆页面的`login.jsp`
    - 用户输入用户名，密码以及验证码
        * 如果用户名和密码输入有误，跳转到登陆页面，提示：用户名或密码错误
        * 如果验证码输入有误，跳转登陆页面，提示：验证码错误
        * 如果全部输入正确，则跳转到主页`success.jsp`，显示：用户名，欢迎您；
            + 如果是第一次登陆，提示：这是您首次登陆！
            + 如果不是第一次登陆，提示：您上次登陆的时间为：时间
    - 用户名和密码都从数据库读出
* */

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置request编码
        request.setCharacterEncoding("utf-8");
        //获取对象
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User loginUser = new User();
        loginUser.setUsername(username);
        loginUser.setPassword(password);

        String checkCode = request.getParameter("checkCode");
        //获取Session
        String checkCode_session = (String) request.getSession().getAttribute("checkCode_session");
        //移除验证码的session
        request.getSession().removeAttribute("checkCode_session");
        //判断生成的验证码和输入的验证码是否相同
        if (checkCode_session.equalsIgnoreCase(checkCode)) {
            //验证码正确
            //判断用户名与登陆密码是否正确
            UserDao dao = new UserDao();
            User user = dao.Login(loginUser);
            if (user != null) {
                //用户名密码相同，登陆成功！
                //判断是否为第一次登陆
                Cookie[] cookies = request.getCookies();
                boolean flag = false;       //设置标记变量，如果是第一次登陆，则为false
                for (Cookie cookie : cookies) {
                    if ("lastTime".equals(cookie.getName())) {
                        //不是第一次登陆
                        flag = true;

                        String str_date = cookie.getValue();
                        request.getSession().setAttribute("date",str_date);

                        //把现在的时间更新到cookie的lastTime中
                        String date = GetUrlDate();
                        cookie.setValue(date);
                        cookie.setMaxAge(60 * 10);   //设置最大存活时间为10分钟
                        response.addCookie(cookie);

                        break;
                    }
                }
                if(flag == false){
                    //第一次登陆
                    String str_date = GetUrlDate();
                    Cookie cookie = new Cookie("lastTime",str_date);
                    cookie.setMaxAge(60 * 10);   //设置最大存活时间为10分钟
                    response.addCookie(cookie);
                }

                //存储用户信息，
                request.getSession().setAttribute("user", username);
                request.getSession().setAttribute("flag",flag);

                // 重定向到success.jsp
                response.sendRedirect(request.getContextPath() + "/success.jsp");
            } else {
                //用户名或密码错误
                //存储信息到request
                request.setAttribute("login_error", "用户名或密码错误");
                //转发到登陆页面
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } else {
            //验证码错误
            //存储信息到request
            request.setAttribute("cc_error", "验证码错误");
            //转发到登陆页面
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    //获取当前时间的URL编码
    public static String GetUrlDate(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String str_date = sdf.format(date);
        try {
            str_date = URLEncoder.encode(str_date,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str_date;
    }

}
