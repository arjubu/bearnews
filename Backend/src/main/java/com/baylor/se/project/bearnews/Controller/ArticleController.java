package com.baylor.se.project.bearnews.Controller;
import java.util.ArrayList;
import com.baylor.se.project.bearnews.Controller.dto.ArticleDto;
import com.baylor.se.project.bearnews.Models.Article;
import com.baylor.se.project.bearnews.Models.ArticleType;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@CrossOrigin("*")
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
    public ResponseEntity<?> getArticles() throws JsonProcessingException{
        ServiceResponseHelper serviceResponseHelper= articleService.getAllArticles(ArticleType.SYSTEM);
        ObjectMapper objectMapper = new ObjectMapper();
        if(serviceResponseHelper.getHasError()){
            return new ResponseEntity<>(objectMapper.writeValueAsString(serviceResponseHelper),HttpStatus.BAD_REQUEST);
        }
     else{
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("data", serviceResponseHelper.getContent());
            return new ResponseEntity<>(map,HttpStatus.OK);
     }
    }

    @RequestMapping(value = "/fetchBaylorNewsArticles", method = RequestMethod.GET)
    public ResponseEntity<?> getBaylorNewsArticles() throws JsonProcessingException{
        ServiceResponseHelper serviceResponseHelper= articleService.getAllArticles(ArticleType.BAYLORNEWS);
        ObjectMapper objectMapper = new ObjectMapper();
        if(serviceResponseHelper.getHasError()){
            return new ResponseEntity<>(objectMapper.writeValueAsString(serviceResponseHelper),HttpStatus.BAD_REQUEST);
        }
        else{
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("data", serviceResponseHelper.getContent());
            return new ResponseEntity<>(map,HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/fetchTweeterArticles", method = RequestMethod.GET)
    public ResponseEntity<?> getTweeterArticles() throws JsonProcessingException{
        ServiceResponseHelper serviceResponseHelper= articleService.getAllArticles(ArticleType.TWITTER);
        ObjectMapper objectMapper = new ObjectMapper();
        if(serviceResponseHelper.getHasError()){
            return new ResponseEntity<>(objectMapper.writeValueAsString(serviceResponseHelper),HttpStatus.BAD_REQUEST);
        }
        else{
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("data", serviceResponseHelper.getContent());
            return new ResponseEntity<>(map,HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/fetchArticleById", method = RequestMethod.GET)
    public ResponseEntity<?> getArticlesById(@RequestParam (name="articleId" , required = false) Long articleId) throws JsonProcessingException{
        ServiceResponseHelper serviceResponseHelper= articleService.findArticleById(articleId, ArticleType.SYSTEM);
        ObjectMapper objectMapper = new ObjectMapper();
        if(serviceResponseHelper.getHasError()){
            return new ResponseEntity<>(objectMapper.writeValueAsString(serviceResponseHelper),HttpStatus.BAD_REQUEST);
        }
        else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("data", serviceResponseHelper.getContent());
            return new ResponseEntity<>(map,HttpStatus.OK);
        }
    }
    @RequestMapping(value = "/fetchBaylorNewsArticleById", method = RequestMethod.GET)
    public ResponseEntity<?> getBaylorNewsArticlesById(@RequestParam (name="articleId" , required = false) Long articleId) throws JsonProcessingException{
        ServiceResponseHelper serviceResponseHelper= articleService.findArticleById(articleId, ArticleType.BAYLORNEWS);
        ObjectMapper objectMapper = new ObjectMapper();
        if(serviceResponseHelper.getHasError()){
            return new ResponseEntity<>(objectMapper.writeValueAsString(serviceResponseHelper),HttpStatus.BAD_REQUEST);
        }
        else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("data", serviceResponseHelper.getContent());
            return new ResponseEntity<>(map,HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/fetchTwitterArticleById", method = RequestMethod.GET)
    public ResponseEntity<?> getTwitterArticlesById(@RequestParam (name="articleId" , required = false) Long articleId) throws JsonProcessingException{
        ServiceResponseHelper serviceResponseHelper= articleService.findArticleById(articleId, ArticleType.TWITTER);
        ObjectMapper objectMapper = new ObjectMapper();
        if(serviceResponseHelper.getHasError()){
            return new ResponseEntity<>(objectMapper.writeValueAsString(serviceResponseHelper),HttpStatus.BAD_REQUEST);
        }
        else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("data", serviceResponseHelper.getContent());
            return new ResponseEntity<>(map,HttpStatus.OK);
        }
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
        List<Long> responseReturned = articleService.getAllTitles(ArticleType.SYSTEM);


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

    @RequestMapping(value = "/fetchBaylorNewsArticleTitles", method = RequestMethod.GET)
    public ResponseEntity<?> getAllBaylorNewsArticleTitle(){
        List<Long> responseReturned = articleService.getAllTitles(ArticleType.BAYLORNEWS);


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

    @RequestMapping(value = "/fetchTwitterArticleTitles", method = RequestMethod.GET)
    public ResponseEntity<?> getAllTwitterArticleTitle(){
        List<Long> responseReturned = articleService.getAllTitles(ArticleType.TWITTER);


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

    @RequestMapping("/fetchArticleAndComments/{id}")
    public ResponseEntity<?> getArticleComment(@PathVariable Long id){
        ServiceResponseHelper serviceResponseHelper = articleService.getArticleComment(id);

        if(serviceResponseHelper.hasError){
            return new ResponseEntity<>(serviceResponseHelper, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(serviceResponseHelper, HttpStatus.OK);
        }
    }
        @RequestMapping(value = "/addlike", method = RequestMethod.GET)
    public ResponseEntity<?> LikeAnArticle( @RequestParam (name="articleId" , required = true) Long articleId) throws JsonProcessingException{
        String responseReturned = articleService.LikeAnArticle(articleId);
        return new ResponseEntity(responseReturned,HttpStatus.OK);

    }

    @RequestMapping(value = "/likecount", method = RequestMethod.GET)
    public ResponseEntity<?> findlikeCount( @RequestParam (name="articleId" , required = true) Long articleId) throws JsonProcessingException{
        Integer responseReturned = articleService.findlikeCount(articleId);
        return new ResponseEntity(responseReturned,HttpStatus.OK);

    }

    @RequestMapping(value = "/addfav", method = RequestMethod.GET)
    public ResponseEntity<?> favAnArticle( @RequestParam (name="articleId" , required = true) Long articleId) throws JsonProcessingException{
        String responseReturned = articleService.favAnArticle(articleId);
        return new ResponseEntity(responseReturned,HttpStatus.OK);

    }


}
