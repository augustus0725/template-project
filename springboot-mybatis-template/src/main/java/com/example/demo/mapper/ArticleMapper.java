package com.example.demo.mapper;

import com.example.demo.pojo.Article;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author canbin.zhang
 * date: 2020/4/7
 */
public interface ArticleMapper {
    @Select("SELECT * FROM ARTICLES WHERE id = #{id}")
    Article getArticle(@Param("id") Long id);
}
