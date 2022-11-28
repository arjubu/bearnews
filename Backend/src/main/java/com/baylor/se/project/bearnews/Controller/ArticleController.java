package com.baylor.se.project.bearnews.Controller;

import com.baylor.se.project.bearnews.Models.Article;
import com.baylor.se.project.bearnews.ResponseObjectMappers.ArticleWithUsersObjectMapper;
import com.baylor.se.project.bearnews.Service.ArticleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @RequestMapping(value = "/populateArticle", method = RequestMethod.POST)
    public ResponseEntity<?> createArticles(@RequestBody Article articleSent) throws JsonProcessingException {

        String responseReturned= articleService.createArticle(articleSent);
        int intValue;
        try {
            intValue = Integer.parseInt(responseReturned);
            return new ResponseEntity<>(responseReturned,HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(responseReturned,HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/fetchArticle", method = RequestMethod.GET)
    public ResponseEntity<?> getArticles(){
        List<ArticleWithUsersObjectMapper> articleLists = articleService.getAllArticles();
        return new ResponseEntity<>(articleLists,HttpStatus.OK);
    }

    @RequestMapping(value = "/fetchArticleById", method = RequestMethod.GET)
    public ResponseEntity<?> getArticlesById(@RequestParam (name="articleId" , required = false) Long articleId){
       Article queryArticle = articleService.fetchArticle(articleId);
       if(queryArticle!=null)
        return new ResponseEntity(queryArticle,HttpStatus.OK);

        return new ResponseEntity<>("article id doesn't exsist",HttpStatus.BAD_REQUEST);
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

}
