package com.baylor.se.project.bearnews.Service;

import com.baylor.se.project.bearnews.Models.Article;

import com.baylor.se.project.bearnews.Models.Tag;

import com.baylor.se.project.bearnews.Models.ArticleType;

import com.baylor.se.project.bearnews.Models.Users;
import com.baylor.se.project.bearnews.Repository.ArticleRepository;
import com.baylor.se.project.bearnews.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    UsersService usersService;

    @Autowired
    TagService tagService;

    public String createArticle(Article article){
        if(article.getContent()=="")
            return "content cannot be null";
        if(article.getTitle()=="")
            return "title cannot be null";
        if(article.getContains()==null)
            return "tag has to be there";
//        if(article.getUsers()==null)
//            return "creater has to be there";
        else {
            long tagId = article.getContains().getId();
            Tag tagsToAttach = tagService.findTagByIdForArticle(tagId);

//            long usersId = article.getUsers().getId();
//            Users usersWhoCreated = usersService.foundUserById(usersId);
//            if(usersWhoCreated==null){
//                return "the user id doesn't exsist";
//            }
            if(tagsToAttach!=null) {
                article.setContains(tagsToAttach);
               // article.setUsers(usersWhoCreated);
                articleRepository.save(article);
            }
            else
                return "please give valid tag or create new one";
        }
        return String.valueOf(article.getId());
    }

    public List<Article> fetchAllArticles(){
        return articleRepository.findAll();
    }

    public Article fetchArticle(Long id){
         Optional<Article> articleQueryOpt= articleRepository.findById(id);
         if(articleQueryOpt.isPresent())
            return articleQueryOpt.get();
         return null;
    }


    public void saveBaylorNews(List<Map> baylorNews){
        List<Article> articles = new ArrayList<>();
        List<Integer> bearNewsIds = new ArrayList<>();

        for(Map m : baylorNews){
            Integer baylorNewsId = Integer.parseInt(m.get("baylorNewsId").toString());
            Optional<Article> existingBaylorNews = articleRepository.findByBaylorNewsId(baylorNewsId);

            if(!existingBaylorNews.isPresent() && !bearNewsIds.contains(baylorNewsId)){
                bearNewsIds.add(baylorNewsId);
                Article article = new Article();

                article.setBaylorNewsId(baylorNewsId); //1%k05OtvEikI
                article.setArticleType(ArticleType.BAYLORNEWS);
                article.setContent(m.get("description").toString());
                article.setTitle(m.get("title").toString());
                article.setDetailLink(m.get("detailLink").toString());
                article.setThumbLink(m.get("thumbnail").toString());

                articles.add(article);
            }
        }

        articleRepository.saveAll(articles);

    }
    public String deleteAnArticle(Long id){
        if(fetchArticle(id)==null)
            return "article id doesn't exsists";
        else{
            articleRepository.deleteById(id);
            return "deleted successfully";
        }
    }
}
