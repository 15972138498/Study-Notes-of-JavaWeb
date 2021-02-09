package com.wzq.service;

import com.wzq.pojo.Books;

import java.util.List;

public interface BookService {

    //service层调dao层

    //增加一本书
    int addBook(Books books);

    //删除一本书
    int deleteBookById(int id);

    //更新一本书
    int updateBook(Books books);

    //查询一本书
    Books queryBookById(int id);

    //查询所有书
    List<Books> queryAllBook();

    //根据name属性模糊查询
    List<Books> queryBookByName(String name);
}
