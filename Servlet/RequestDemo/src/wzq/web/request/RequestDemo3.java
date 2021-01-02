package wzq.web.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/requestDemo3")
public class RequestDemo3 extends HttpServlet {

    /*e
    * 获取请求体数据：
    * 1、字符流：BufferedReader getReader()，只能操作字符数据
    * 2、字节流：ServletInputStream getInputStream()，可以操作所有类型数据
    * */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1、获取流对象
        BufferedReader reader = request.getReader();
        //2、从流中读取数据
        String line = null;
        while((line = reader.readLine()) !=null ){
            System.out.println(line);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
