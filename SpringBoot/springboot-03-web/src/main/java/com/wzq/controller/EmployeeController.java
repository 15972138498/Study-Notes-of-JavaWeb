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
