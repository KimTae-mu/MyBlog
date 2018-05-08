package org.kim.controller;

import org.kim.entity.Article;
import org.kim.entity.Category;
import org.kim.entity.User;
import org.kim.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.tautua.markdownpapers.Markdown;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
@RequestMapping("/Article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/")
    public String index(Model model) {
        System.out.println("index xxx");
        List<Article> articles = articleService.getFirst10Articles();
        for (Article article : articles) {
            System.out.println(article.getCategory());
        }
        model.addAttribute("articles", articles);
        return "index";
    }

    @RequestMapping("/column/{displayName}/{category}")
    public String column(@PathVariable("category") String category, Model model, @PathVariable("displayName") String displayName) {
        model.addAttribute("articles", articleService.getArticlesByCategoryName(category));
        model.addAttribute("displayName", displayName);
        return "columnPage";
    }

    @RequestMapping("/detail/{id}/{category}")
    public String detail(@PathVariable("id") Long id, Model model) {
        Article article = articleService.getArticleById(id);
        System.out.println(article.getContent());
        Markdown markdown = new Markdown();
        try {
            StringWriter out = new StringWriter();
            markdown.transform(new StringReader(article.getContent()), out);
            out.flush();
            article.setContent(out.toString());
            System.out.println("------------------------");
            System.out.println(article.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("article", article);
        return "detail";
    }

    @RequestMapping("/kim")
    public String admin(Model model) {
        model.addAttribute("articles", articleService.getFirst10Articles());
        return "../admin/index";
    }

    @RequestMapping("/write")
    public String write(Model model) {
        List<Category> categories = articleService.getCategories();
        categories.remove(0);
        model.addAttribute("categories", categories);
        return "../admin/write";
    }

    @RequestMapping(value = "/write", method = RequestMethod.POST)
    public String write(Article article) {
        if (article.getId() == null) {
            articleService.writeBlog(article);
        }else {
            articleService.updateBlog(article);
        }
        return "redirect:/Article/kim";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        articleService.deleteArticleById(id);
        return "redirect:/Article/kim";
    }

    @RequestMapping("/update/{id}")
    public String update(@PathVariable("id") Long id,Model model){
        Article article = articleService.getArticleById(id);
        model.addAttribute("article",article);
        return "../admin/write";
    }

    @RequestMapping("/search")
    public String Search(String search,Model model){
        List<Article> articles1 = articleService.getArticlesBySummary(search);
        List<Article> articles2 = articleService.getArticlesByTitle(search);
        articles2.removeAll(articles1);
        articles1.addAll(articles2);

        model.addAttribute("articles",articles1);
        return "SearchResult";
    }

/*    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String uploadFile(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam(value = "editormd-image-file", required = false) MultipartFile attach) {
        try {
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type","text/html");
            String realPath = request.getSession().getServletContext().getRealPath("/resources/upload/");

            File filepath = new File(realPath);
            if(!filepath.exists()){
                filepath.mkdirs();
            }

            File realFile = new File(realPath+File.separator+attach.getOriginalFilename());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }*/
}
