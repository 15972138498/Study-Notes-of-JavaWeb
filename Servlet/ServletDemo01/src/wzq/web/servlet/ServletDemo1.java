package wzq.web.servlet;

import javax.servlet.*;
import java.io.IOException;

public class ServletDemo1 implements Servlet {

    /*
    * 初始化方法
    * 在Servlet被创建时执行，只执行一次
    * */
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    /*
    * 获取ServletConfig对象
    * */
    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    /*
    * 提供服务
    * 每一次Servlet被访问时，都会执行，执行N次
    * */
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("service is run.....");
    }

    /*
    * 获取Servlet版本、作者等信息
    * */
    @Override
    public String getServletInfo() {
        return null;
    }
    /*
    * 销毁
    * 在Servlet对象被杀死时执行，只执行一次
    * */
    @Override
    public void destroy() {

    }
}
