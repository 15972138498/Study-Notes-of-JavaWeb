package wzq.web.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

@WebServlet("/requestDemo4")
public class RequestDemo4 extends HttpServlet {

    /*
     * Request 对象获取请求参数的通用方式
     * 1、String getParameter(String name) 根据参数名称获取参数值
     * 2、String[] getParameterValues(String name) 根据参数名称获取参数值的数组
     * 3、Enumeration<String> getParameterNames() 获取所有请求参数的名称
     * 4、Map<String,String[]> getParameterMap() 获取所有参数的Map集合
     * */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //0、解决中文乱码问题
        request.setCharacterEncoding("utf-8");

        System.out.println("1、根据参数名称获取参数值：");
        //1、根据参数名称获取参数值
        String username = request.getParameter("username");
        System.out.println("username : " + username);
        String password = request.getParameter("password");
        System.out.println("password : " + password);
        String hobby1 = request.getParameter("hobby");
        System.out.println("hobby : " + hobby1);

        System.out.println("-----------------------------------------------------");

        System.out.println("2、根据参数名称获取参数值的数组：");
        //2、根据参数名称获取参数值的数组
        String[] hobbies = request.getParameterValues("hobby");
        for (String hobby : hobbies) {
            System.out.println("hobby : " + hobby);
        }

        System.out.println("-----------------------------------------------------");
        //3、获取所有请求参数的名称
        System.out.println("3、获取所有请求参数的名称");
        Enumeration<String> parameterNames = request.getParameterNames();
        while(parameterNames.hasMoreElements()){
            String name = parameterNames.nextElement();
            String value = request.getParameter(name);
            System.out.println(name + " : " + value);
        }

        System.out.println("-----------------------------------------------------");
        //4、获取所有参数的Map集合
        System.out.println("4、获取所有参数的Map集合");
        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<String> keySet = parameterMap.keySet();
        for(String name : keySet){
            String[] strings = parameterMap.get(name);
            System.out.print(name + ": ");
            for (String value : strings){
                System.out.print(value + " ");
            }
            System.out.println();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
