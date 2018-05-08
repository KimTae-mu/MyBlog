package org.kim.service;

import org.kim.entity.Article;
import org.kim.entity.Category;

import java.util.List;

public interface ArticleService {
    List<Article> getFirst10Articles();

    List<Article> getArticlesByCategoryName(String name);

    Article getArticleById(Long id);

    List<Category> getCategories();

    void writeBlog(Article article);

    void updateBlog(Article article);

    void deleteArticleById(Long id);

    List<Article> getArticlesBySummary(String search);

    List<Article> getArticlesByTitle(String search);
}
