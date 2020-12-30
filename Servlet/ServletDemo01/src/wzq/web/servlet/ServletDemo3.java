package wzq.web.servlet;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/demo3")
public class ServletDemo3 extends GenericServlet {
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

        /*
            String method = req.getMethod();
            //get方式获取数据
            if("GET".equals(method)){
                //执行相应操作
            }
            //POST方式获取数据
            else if("POST".equals(method)){
                //执行相应操作
            }

        */
    }
}
