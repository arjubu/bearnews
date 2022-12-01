package com.baylor.se.project.bearnews.Service;

import com.baylor.se.project.bearnews.Controller.ServiceResponseHelper;
import com.baylor.se.project.bearnews.Controller.dto.ArticleDto;
import com.baylor.se.project.bearnews.Controller.dto.ArticleResponseDto;
import com.baylor.se.project.bearnews.Models.Article;

import com.baylor.se.project.bearnews.Models.Tag;

import com.baylor.se.project.bearnews.Models.ArticleType;

import com.baylor.se.project.bearnews.Models.Users;
import com.baylor.se.project.bearnews.Repository.ArticleRepository;
import com.baylor.se.project.bearnews.Repository.UsersRepository;
import com.baylor.se.project.bearnews.ResponseObjectMappers.ArticleWithUsersObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    UsersService usersService;

    @Autowired
    TagService tagService;


    public ServiceResponseHelper createArticle(ArticleDto articleDto){
        Article article = new Article();

        ServiceResponseHelper serviceResponseHelper = new ServiceResponseHelper(false,null,null);
        Map errorResponse = new HashMap<>();
        Map successResponse = new HashMap<>();

        long tagId = articleDto.getTagContaiedId();
        Tag tagsToAttach = tagService.findTagByIdForArticle(tagId);
        long usersId = articleDto.getCreatedUsersId();
        Users usersWhoCreated = usersService.foundUserById(usersId);

        if(articleDto.getArticleTitle()==""){
            serviceResponseHelper.setHasError(true);
            errorResponse.put("message", "article title cannot be empty");
            serviceResponseHelper.setResponseMessage(errorResponse);
            serviceResponseHelper.setContent(null);
            return serviceResponseHelper;
        }
        if(articleDto.getArticleContent()==""){
            serviceResponseHelper.setHasError(true);
            errorResponse.put("message", "article content cannot be empty");
            serviceResponseHelper.setResponseMessage(errorResponse);
            serviceResponseHelper.setContent(null);
             return serviceResponseHelper;
        }

        if(articleDto.getArticleContent().length()<=0 || articleDto.getArticleContent().length()>5000){
            serviceResponseHelper.setHasError(true);
            errorResponse.put("message", "content size excedeed");
            serviceResponseHelper.setResponseMessage(errorResponse);
            serviceResponseHelper.setContent(null);
             return serviceResponseHelper;
        }
        if(articleDto.getTagContaiedId()==0){
            serviceResponseHelper.setHasError(true);
            errorResponse.put("message", "article must contain a tag");
            serviceResponseHelper.setResponseMessage(errorResponse);
            serviceResponseHelper.setContent(null);
             return serviceResponseHelper;
        }

        if(articleDto.getCreatedUsersId()==0){
            serviceResponseHelper.setHasError(true);
            errorResponse.put("message", "article must have a user");
            serviceResponseHelper.setResponseMessage(errorResponse);
            serviceResponseHelper.setContent(null);
            return serviceResponseHelper;
        }

        if(usersWhoCreated==null){
            serviceResponseHelper.setHasError(true);
            errorResponse.put("message", "valid user id isn't provided");
            serviceResponseHelper.setResponseMessage(errorResponse);
            serviceResponseHelper.setContent(null);
            return serviceResponseHelper;
            }
        if(tagsToAttach==null) {
            serviceResponseHelper.setHasError(true);
            errorResponse.put("message", "the given tag is not valid");
            serviceResponseHelper.setResponseMessage(errorResponse);
            serviceResponseHelper.setContent(null);
            return serviceResponseHelper;
            }
        else {
            LocalDateTime articleCreatedAt = LocalDateTime.now();
            article.setTitle(articleDto.getArticleTitle());
            article.setContent(articleDto.getArticleContent());
            article.setCreatedAt(articleCreatedAt);
//            article.setDetailLink(articleDto.getArticleDetailLink());
//            article.setThumbLink(articleDto.getArticleThumbLink());
            article.setContains(tagsToAttach);

            articleRepository.save(article);
            long articleId = article.getId();
            if(articleId!=0){
                List<Article> listInserted = new ArrayList<>();
                listInserted.add(article);
                usersService.usersArticleAttach(usersWhoCreated.getId(),article);

                serviceResponseHelper.setHasError(false);
                successResponse.put("message", "article created sucessfully");
                serviceResponseHelper.setResponseMessage(successResponse);

                ArticleResponseDto articleResponse = new ArticleResponseDto();;
                articleResponse.setIdOfArticle(article.getId());
                articleResponse.setIdOfTag(article.getContains().getId());
                articleResponse.setTextOfTag(article.getContains().getTagText());
                articleResponse.setTitleOfArticle(article.getTitle());
                articleResponse.setContentOfArticle(article.getContent());
                //articleResponse.setTimeOfArticleCreation(article.getCreatedAt());
                articleResponse.setIdOfCreator(usersWhoCreated.getId());
                articleResponse.setFirstNameofCreator(usersWhoCreated.getFirstName());
                articleResponse.setLastNameofCreator(usersWhoCreated.getLastName());
                serviceResponseHelper.setContent(articleResponse);
                return serviceResponseHelper;
            }
            else{
                serviceResponseHelper.setHasError(true);
                errorResponse.put("message", "article couldn't be created");
                serviceResponseHelper.setResponseMessage(errorResponse);
                serviceResponseHelper.setContent(null);
                return serviceResponseHelper;
            }
        }
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

    public List<ArticleWithUsersObjectMapper> findArtcilesByTags(Long tagId){
       List<Article> allArticles = articleRepository.findArticlesByContains_Id(tagId);
       List<ArticleWithUsersObjectMapper> articleDetails = new ArrayList<>();

        if(allArticles.isEmpty()==false){
            for(Article a: allArticles){
                ArticleWithUsersObjectMapper article = new ArticleWithUsersObjectMapper();

                article.setIdOfArticle(a.getId());
                article.setTitleOfArticle(a.getTitle());
                article.setContentOfArticle(a.getContent());
                article.setTimeOfArticleCretion(a.getCreatedAt());

                Users author= usersService.findingArticlesByUser(a.getId());
                article.setIdOfCreator(author.getId());
                article.setNameofCreator(author.getFirstName());

                article.setIdOfTag(a.getContains().getId());
                article.setTextOfTag(a.getContains().getTagText());

                articleDetails.add(article);
            }
        }
        return articleDetails;

    }
    public List<ArticleWithUsersObjectMapper> getAllArticles(){
//        ServiceResponseHelper serviceResponseHelper = new ServiceResponseHelper(false,null,null);
//        Map errorResponse = new HashMap<>();
//        Map successResponse = new HashMap<>();
//
//        List<Users> systemUsersArticles = usersService.findingUsersCreatedArticles();
//        if(systemUsersArticles.isEmpty()){
//            return null;
//        }
//        List<ArticleWithUsersObjectMapper> articleDetails = new ArrayList<>();
//        for(Users us: systemUsersArticles){
//            for(Article a: us.getArticles()){
//                ArticleWithUsersObjectMapper article = new ArticleWithUsersObjectMapper();
//                article.setTitleOfArticle(a.getTitle());
//                article.setContentOfArticle(a.getContent());
//                article.setIdOfArticle(a.getId());
//                article.setTimeOfArticleCretion(a.getCreatedAt());
//
//                article.setNameofCreator(us.getFirstName());
//                article.setIdOfCreator(us.getId());
//
//                article.setIdOfTag(a.getContains().getId());
//                article.setTextOfTag(a.getContains().getTagText());
//
//                articleDetails.add(article);
//            }
//        }
//
//        return articleDetails;
        List<Article> articleList = articleRepository.findAll();
        if(articleList.isEmpty()){
            return null;
        }
        List<ArticleWithUsersObjectMapper> articleDetails = new ArrayList<>();
        for(Article a: articleList){
            ArticleWithUsersObjectMapper article = new ArticleWithUsersObjectMapper();
                article.setTitleOfArticle(a.getTitle());
                article.setContentOfArticle(a.getContent());
                article.setIdOfArticle(a.getId());
                article.setTimeOfArticleCretion(a.getCreatedAt());

                Users author= usersService.findingArticlesByUser(a.getId());
                article.setIdOfCreator(author.getId());
                article.setNameofCreator(author.getFirstName());

                article.setIdOfTag(a.getContains().getId());
                article.setTextOfTag(a.getContains().getTagText());

                articleDetails.add(article);
        }
        return articleDetails;

    }
}
