package org.kim.service.impl;

import org.kim.dao.ArticleDao;
import org.kim.entity.Article;
import org.kim.entity.Category;
import org.kim.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    public List<Article> getFirst10Articles() {
        return articleDao.getFirst10Article();
    }

    @Override
    public List<Article> getArticlesByCategoryName(String name) {
        Long categoryId = articleDao.getCategoryIdByName(name);
        List<Article> articles = articleDao.getArticlesByCategoryName(categoryId);

        return articles;
    }

    @Override
    public Article getArticleById(Long id) {
        return articleDao.getArticleById(id);
    }

    @Override
    public List<Category> getCategories() {
        return articleDao.getCategories();
    }

    @Override
    public void writeBlog(Article article) {
        Long categoryId = articleDao.getCategoryIdByName(article.getCategory());
        article.setCategoryId(categoryId);
        article.setDate(sdf.format(new Date()));
        if (article.getSummary() == null || "".equals(article.getSummary())) {
            if (article.getContent().length() > 20) {
                article.setSummary(article.getContent().substring(0, 20));
            } else {
                article.setSummary(article.getContent().substring(0, article.getContent().length()));
            }
        }
        articleDao.writeBlog(article);
    }

    @Override
    public void updateBlog(Article article) {
        article.setDate(sdf.format(new Date()));
        if(article.getSummary() == null || "".equals(article.getSummary())){
            if(article.getContent().length()>20){
                article.setSummary(article.getContent().substring(0,20));
            }else {
                article.setSummary(article.getContent().substring(0,article.getContent().length()));
            }
        }
        articleDao.updateArticleById(article);
    }

    @Override
    public void deleteArticleById(Long id) {
        articleDao.deleteArticleById(id);
    }

    @Override
    public List<Article> getArticlesBySummary(String search) {
        return articleDao.getArticlesBySummary(search);
    }

    @Override
    public List<Article> getArticlesByTitle(String search) {
        return articleDao.getArticlesByTitle(search);
    }
}
