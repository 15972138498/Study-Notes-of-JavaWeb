package com.wzq.service.impl;

import com.wzq.dao.UserDao;
import com.wzq.dao.impl.UserDaoImpl;
import com.wzq.domain.PageBean;
import com.wzq.domain.User;
import com.wzq.service.UserService;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {

    private UserDao dao = new UserDaoImpl();

    //查询所有用户
    @Override
    public List<User> findAll() {
        //调用dao层findAll
        return dao.findAll();
    }

    @Override
    public User loginByUserNameAndPassword(User loginUser) {
        return dao.loginByUserNameAndPassword(loginUser);
    }

    @Override
    public void add(User user) {
        //调用dao层方法
        dao.add(user);
    }

    @Override
    public void delete(String id) {
        //调用dao层删除方法
        dao.delete(Integer.parseInt(id));
    }

    @Override
    public User findById(String id) {
        return dao.findById(Integer.parseInt(id));
    }

    @Override
    public void update(User user) {
        dao.update(user);
    }

    @Override
    public void delSelectedUser(String[] ids) {
        if (ids != null && ids.length > 0) {
            for (String id : ids) {
                dao.delete(Integer.parseInt(id));
            }
        }
    }

    @Override
    public PageBean<User> findUserByPage(String _currentPage, String _rows, Map<String, String[]> condition) {
        int currentPage = Integer.parseInt(_currentPage);
        int rows = Integer.parseInt(_rows);

        //创建新的PageBean对象
        PageBean page = new PageBean();
        //设置page的属性
        page.setCurrentPage(currentPage);
        page.setRows(rows);
        //调用dao查询totalCount的总记录数
        int totalCount = dao.findTotalCount(condition);
        page.setTotalCount(totalCount);
        //求start
        int start = (currentPage - 1) * rows;
        //调用dao查询list集合
        List<User> list = dao.findByPage(start, rows,condition);
        page.setList(list);
        //计算总页码
        int totalPage = (totalCount % rows == 0) ? (totalCount / rows) : (totalCount / rows + 1);
        page.setTotalPage(totalPage);
        return page;
    }


}
