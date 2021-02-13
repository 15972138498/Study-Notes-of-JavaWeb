@[toc]


-----

完整代码地址：[https://github.com/lesileqin/Study-Notes-of-JavaWeb/tree/master/SpringBoot/springboot-03-web](https://github.com/lesileqin/Study-Notes-of-JavaWeb/tree/master/SpringBoot/springboot-03-web)

# 一、效果展示
登录页
![在这里插入图片描述](https://img-blog.csdnimg.cn/202102131547579.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
员工列表列
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210213154804401.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
修改或添加页：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210213154814271.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
# 二、目录结构
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210213155408228.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)


# 三、主要代码
`LoginController`：
```java
package com.wzq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {

    //以post的方式处理请求
    @PostMapping("/user/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Map<String,Object> map, HttpSession session){
        if(!StringUtils.isEmpty(username) && "123456".equals(password)){
            session.setAttribute("loginUser",username);
            //登陆成功，为了防止表单重复提交，可以重定向到主页
            return "redirect:/main.html";
        }else{
            //登陆失败
            map.put("msg","用户名或密码错误！");
            return "login";
        }
    }
}
```
`EmployeeController`：
```java
package com.wzq.controller;

import com.wzq.dao.DepartmentDao;
import com.wzq.dao.EmployeeDao;
import com.wzq.entities.Department;
import com.wzq.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
public class EmployeeController {

    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    DepartmentDao departmentDao;

    //查询所有员工，返回列表页面
    @GetMapping("/emps")
    public String list(Model model){
        Collection<Employee> all = employeeDao.getAll();
        model.addAttribute("emps",all);
        return "emp/list";
    }

    //来到员工添加页面
    @GetMapping("/emp")
    public String toAddPage(Model model){
        //来到添加页面之前，查出所有部门，在页面上显示
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts",departments);
        return "emp/add";
    }

    //添加员工
    //SpringMVC自动将请求参数和参入对象的属性进行一一绑定；
    //要求了请求参数的名字和javebean入参对象里面的属性名是一致的
    @PostMapping("/emp")
    public String addEmp(Employee employee){

        System.out.println("保存的员工信息："+employee);
        //保存员工
        employeeDao.save(employee);
        //添加完，去到列表页面
        //redirect ：重定向
        //forward ：转发
        return "redirect:/emps";
    }

    //来到修改页面，查出当前员工，在页面中回显
    @GetMapping("/emp/{id}")
    public String toEditPage(@PathVariable("id") Integer id,Model model){
        Employee employee = employeeDao.get(id);
        model.addAttribute("emp",employee);
        //页面要显示所有的部门列表
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts",departments);
        //回到修改页面，修改添加、二合一
        return "emp/add";
    }

    //修改
    @PutMapping("/emp")
    public String Edit(Employee employee){

        System.out.println(employee);

        employeeDao.save(employee);
        return "redirect:/emps";    //重定向
    }

    //删除
    @RequestMapping("/empDelete/{id}")
    public String delete(@PathVariable("id") Integer id){
        employeeDao.delete(id);
        return "redirect:/emps";
    }

}
```
`LoginHandlerInterceptor`：
```java
package com.wzq.component;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
* 登陆检查
* */

public class LoginHandlerInterceptor implements HandlerInterceptor {
    //目标方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object user = request.getSession().getAttribute("loginUser");
        if(user == null){
            //未登录，返回登陆页面，拦截
            request.setAttribute("msg","没有权限请登录！");
            request.getRequestDispatcher("/index.html").forward(request,response);
            return false;
        }else{
            //已登陆，放行
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
```
`MyMvcConfig`:
```java
package com.wzq.config;

import com.wzq.component.LoginHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //浏览器发送 / 或 /login.html 访问到 index
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/index.html").setViewName("login");

        //请求main.html 跳转到 dashboard.html
        registry.addViewController("/main.html").setViewName("dashboard");
    }

    //注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*
        * addPathPatterns：拦截的请求
        * excludePathPatterns：排除哪些请求
        * 如果是/*： 静态资源：*.css  *.js，SpringBoot已经做好了静态资源映射，这里不用排除,!!!，只过滤下一级目录
        * 如果是/** ：需要排除，过滤所有目录
         */
        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/","/index.html","/user/login","/asserts/css/**","/asserts/js/**","/asserts/img/**");
    }
}
```
