package org.kim.dao;

import org.apache.ibatis.annotations.Param;
import org.kim.entity.Article;
import org.kim.entity.Category;

import java.util.List;

public interface ArticleDao {
    public Article getArticleById(@Param("id") Long id);

    public List<Article> getFirst10Article();

    public List<Article> getArticlesByCategoryName(Long categoryId);

    public List<Category> getCategories();

    public void writeBlog(Article article);

    public Long getCategoryIdByName(String name);

    public void deleteArticleById(Long id);

    public void updateArticleById(Article article);

    public Category getCategoryById(Long id);

    List<Article> getArticlesBySummary(String search);

    List<Article> getArticlesByTitle(String search);
}
