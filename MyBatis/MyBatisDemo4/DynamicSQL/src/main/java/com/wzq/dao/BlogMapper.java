package com.wzq.dao;

import com.wzq.pojo.Blog;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface BlogMapper {

    int addBlog(Blog blog);

    @Select("select * from blog")
    List<Blog> getAll();

    // 使用if标签，查询所有author字段带“狂”的和title字段带有'a'的
    List<Blog> queryBlogByIf(Map map);

    List<Blog> queryBlogByWhere(Map map);

    //更新`id=3`后面的其他字段
    int updateBlogBySet(Blog blog);

    //传入了title就按title查，传入了author就按author查，否则按照views查
    List<Blog> queryBlogByChoose(Map map);

}
