package com.baylor.se.project.bearnews.Service;

import com.baylor.se.project.bearnews.Models.Article;
import com.baylor.se.project.bearnews.Repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    public void createArticle(Article article){
        if(article.getContent()=="")
            return;
        if(article.getTitle()=="")
            return;
        else
            articleRepository.save(article);
    }

    public List<Article> fetchAllarticles(){
        return articleRepository.findAll();
    }

    public Article fetchArticle(Long id){
         Optional<Article> articleQueryOpt= articleRepository.findById(id);
         if(articleQueryOpt.isPresent())
            return articleQueryOpt.get();
         return null;
    }
}
