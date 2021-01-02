package wzq.web.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/requestDemo1")
public class RequestDemo1 extends HttpServlet {

    /*
     * 获取请求行数据  GET /day14/demo1?name=xxxx HTTP/1.1
     *
     * 1、获取请求方式：String getMethod();
     * 2、（*）获取虚拟目录：String getContextPath();
     * 3、获取Servlet路径：String getServletPath();
     * 4、获取get方式请求参数，例如：name=xxx
     *       String getQueryString();
     * 5、获取请求URI和URL：
     *       String getRequestURI();
     *       String getRequestURL();
     * 6、获取协议及版本：String getProtocol();
     * 7、获取客户机IP地址：String getRemoteAddr();
     * */

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1、获取请求方式：String getMethod();
        String method = request.getMethod();
        System.out.println("请求方式：" + method);

        //2、（*）获取虚拟目录：String getContextPath();
        String contextPath = request.getContextPath();
        System.out.println("虚拟目录：" + contextPath);

        //3、获取Servlet路径：String getServletPath();
        String servletPath = request.getServletPath();
        System.out.println("Servlet路径：" + servletPath);

        //4、获取get方式请求参数，例如：name=xxx
        //   String getQueryString();
        String queryString = request.getQueryString();
        System.out.println("get方式获取参数：" + queryString);

        /*
         * 5、获取请求URI和URL：
         *       String getRequestURI();
         *       String getRequestURL();
         * */
        String requestURI = request.getRequestURI();
        StringBuffer requestURL = request.getRequestURL();
        System.out.println("URI：" + requestURI);
        System.out.println("URL：" + requestURL);

        //6、获取协议及版本：String getProtocol();
        String protocol = request.getProtocol();
        System.out.println("协议：" + protocol);

        //7、获取客户机IP地址：String getRemoteAddr();
        String remoteAddr = request.getRemoteAddr();
        System.out.println("客户机IP地址：" + remoteAddr);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
