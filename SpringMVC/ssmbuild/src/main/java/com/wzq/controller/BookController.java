package com.wzq.controller;

import com.wzq.pojo.Books;
import com.wzq.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    //controller层调service层
    @Autowired
    private BookService bookService;

    //查询所有书籍
    @RequestMapping("/allBook")
    public String AllBook(Model model){
        List<Books> list = bookService.queryAllBook();
        model.addAttribute("list",list);
        return "allBook";
    }

    //跳转到增加书籍页面
    @RequestMapping("/toAddBook")
    public String toAddBook(){
        return "addBook";
    }

    //添加书籍
    @RequestMapping("/addBook")
    public String addBook(Books book){
        System.out.println(book);
        bookService.addBook(book);
        return "redirect:/book/allBook";
    }

    //跳转到修改书籍页面
    @RequestMapping("/toUpdateBook")
    public String toUpdateBook(int id, Model model){
        System.out.println("BookController => updateBook => id = " + id);
        Books book = bookService.queryBookById(id);
        System.out.println("BookController => updateBook => book = " + book);
        //数据回显
        model.addAttribute("book",book);
        return "updateBook";
    }

    //修改书籍
    @RequestMapping("/updateBook")
    public String updateBook(Books book){
        bookService.updateBook(book);
        return "redirect:/book/allBook";
    }

    //删除书籍
    @RequestMapping("/deleteBook")
    public String deleteBook(int id){
        bookService.deleteBookById(id);
        return "redirect:/book/allBook";
    }

    //模糊查询
    @RequestMapping("/queryBookByName")
    public String queryBookByName(String name,Model model){
        List<Books> list = bookService.queryBookByName(name);
        model.addAttribute("list",list);
        return "allBook";
    }

}
