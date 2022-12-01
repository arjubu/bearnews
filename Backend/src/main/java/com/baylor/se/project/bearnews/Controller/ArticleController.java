package com.baylor.se.project.bearnews.Controller;
import java.util.ArrayList;
import com.baylor.se.project.bearnews.Controller.dto.ArticleDto;
import com.baylor.se.project.bearnews.Models.Article;
import com.baylor.se.project.bearnews.Models.Users;
import com.baylor.se.project.bearnews.ResponseObjectMappers.ArticleWithUsersObjectMapper;
import com.baylor.se.project.bearnews.Service.ArticleService;
import com.baylor.se.project.bearnews.Service.UsersService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ArticleController {

    @Autowired
    ArticleService articleService;


    @RequestMapping(value = "/createArticle", method = RequestMethod.POST)
    public ResponseEntity<?> createArticles(@RequestBody ArticleDto articleSent) throws JsonProcessingException {

       ServiceResponseHelper serviceResponseHelper= articleService.createArticle(articleSent);
       ObjectMapper objectMapper = new ObjectMapper();
        if(serviceResponseHelper.getHasError()){
            return new ResponseEntity<>(objectMapper.writeValueAsString(serviceResponseHelper),HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>(objectMapper.writeValueAsString(serviceResponseHelper),HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/fetchSystemArticles", method = RequestMethod.GET)
    public ResponseEntity<?> getArticles(){
    List<ArticleWithUsersObjectMapper> responseReturned = articleService.getAllArticles();
    if(responseReturned==null){
        return new ResponseEntity<>(responseReturned,HttpStatus.BAD_REQUEST);
    }
     else{
        return new ResponseEntity<>(responseReturned,HttpStatus.OK);
     }
    }

    @RequestMapping(value = "/fetchArticleById", method = RequestMethod.GET)
    public ResponseEntity<?> getArticlesById(@RequestParam (name="articleId" , required = false) Long articleId){
       Article queryArticle = articleService.fetchArticle(articleId);
       if(queryArticle!=null)
        return new ResponseEntity(queryArticle,HttpStatus.OK);

        return new ResponseEntity<>(queryArticle,HttpStatus.BAD_REQUEST);
    }
    @RequestMapping(value = "/deleteArticleById", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAnArticle( @RequestParam (name="articleId" , required = true) Long articleId) throws JsonProcessingException{
      String responseReturned = articleService.deleteAnArticle(articleId);
        return new ResponseEntity(responseReturned,HttpStatus.OK);

    }

    @RequestMapping(value = "/fetchArticleByTagId", method = RequestMethod.GET)
    public ResponseEntity<?> getArticlesByTag(@RequestParam (name="tagId" , required = false) Long tagId){
      List<ArticleWithUsersObjectMapper> sentArticles= articleService.findArtcilesByTags(tagId);
        if(sentArticles.isEmpty()==false)
            return new ResponseEntity(sentArticles,HttpStatus.OK);

        return new ResponseEntity<>("doesn't contains any article with id",HttpStatus.BAD_REQUEST);

    }

    @RequestMapping(value = "/fetchAllArticles", method = RequestMethod.GET)
    public ResponseEntity<?> getAllArticles(){
        List<Article> responseReturned = articleService.getAll();
        if(responseReturned==null){
            return new ResponseEntity<>(responseReturned,HttpStatus.BAD_REQUEST);
        }
        else{
            return new ResponseEntity<>(responseReturned,HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/fetchArticleTitles", method = RequestMethod.GET)
    public ResponseEntity<?> getAllArticleTitlwa(){
        List<Long> responseReturned = articleService.getAllTitles();


        if(responseReturned==null){
            return new ResponseEntity<>(responseReturned,HttpStatus.BAD_REQUEST);
        }
        else{
//            for (String i : responseReturned
//            ) {
//
//                response.add(i.replace(" ","-"));
//
//            }
            return new ResponseEntity<>(responseReturned,HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/fetchTitleArticles", method = RequestMethod.POST)
    public ResponseEntity<?> getArticleByTitles(@RequestBody Map<String, String> json){

        String titleSent = json.get("title");
        System.out.println(titleSent);
        Article responseReturned = articleService.getArticlesByTitle(titleSent);
        if(responseReturned==null){
            return new ResponseEntity<>(responseReturned,HttpStatus.BAD_REQUEST);
        }
        else{
            return new ResponseEntity<>(responseReturned,HttpStatus.OK);
        }
    }


}
