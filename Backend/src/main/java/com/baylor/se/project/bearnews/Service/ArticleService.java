package com.baylor.se.project.bearnews.Service;

import com.baylor.se.project.bearnews.Models.Article;
import com.baylor.se.project.bearnews.Models.Tag;
import com.baylor.se.project.bearnews.Repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    TagService tagService;

    public String createArticle(Article article){
        if(article.getContent()=="")
            return "content cannot be null";
        if(article.getTitle()=="")
            return "title cannot be null";
        if(article.getContains()==null)
            return "tag has to be there";
        else {
            long tagId = article.getContains().getId();
            Tag tagsToAttach = tagService.findTagByIdForArticle(tagId);
            if(tagsToAttach!=null) {
                article.setContains(tagsToAttach);
                articleRepository.save(article);
            }
            else
                return "please give valid tag or create new one";
        }
        return String.valueOf(article.getId());
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
