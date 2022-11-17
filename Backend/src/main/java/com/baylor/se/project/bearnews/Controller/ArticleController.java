package com.baylor.se.project.bearnews.Controller;

import com.baylor.se.project.bearnews.Models.Article;
import com.baylor.se.project.bearnews.Service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @RequestMapping(value = "/populateArticle", method = RequestMethod.POST)
    public ResponseEntity<?> createArticles(@RequestBody Article articleSent){
        articleService.createArticle(articleSent);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/fetchArticle", method = RequestMethod.GET)
    public ResponseEntity<?> getArticles(){
        List<Article> articleList= articleService.fetchAllarticles();
        return new ResponseEntity(articleList,HttpStatus.OK);
    }
}
