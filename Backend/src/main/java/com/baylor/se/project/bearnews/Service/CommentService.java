package com.baylor.se.project.bearnews.Service;

import com.baylor.se.project.bearnews.Controller.ServiceResponseHelper;
import com.baylor.se.project.bearnews.Controller.dto.CommentDto;
import com.baylor.se.project.bearnews.Models.Article;
import com.baylor.se.project.bearnews.Models.Comment;
import com.baylor.se.project.bearnews.Models.Users;
import com.baylor.se.project.bearnews.Repository.ArticleRepository;
import com.baylor.se.project.bearnews.Repository.CommentRepository;
import com.baylor.se.project.bearnews.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    UsersRepository usersRepository;

    public ServiceResponseHelper createComment(CommentDto commentdto,Long articleId){
        ServiceResponseHelper serviceResponseHelper = new ServiceResponseHelper(false,null,null);
        Map errorResponse = new HashMap<>();
        Map successResponse = new HashMap<>();

        if(commentdto.getCommentText()=="") {
            serviceResponseHelper.setHasError(true);
            errorResponse.put("message", "comment body couldn't be empty");
            serviceResponseHelper.setResponseMessage(errorResponse);
            serviceResponseHelper.setContent(null);
            return serviceResponseHelper;
        }
        if(commentdto.getCommentEmail()=="") {
            serviceResponseHelper.setHasError(true);
            errorResponse.put("message", "commentor email not there");
            serviceResponseHelper.setResponseMessage(errorResponse);
            serviceResponseHelper.setContent(null);
            return serviceResponseHelper;
        }
        Optional<Article> articleListOpt = articleRepository.findById(articleId);
        if(articleListOpt.isEmpty()){
            serviceResponseHelper.setHasError(true);
            errorResponse.put("message", "article is not valid");
            serviceResponseHelper.setResponseMessage(errorResponse);
            serviceResponseHelper.setContent(null);
            return serviceResponseHelper;
        }
        else {

           // commentRepository.save(comment);
            String uEmail = commentdto.getCommentEmail();
            Optional<Users> commentorOpt = usersRepository.findByEmail(uEmail);
            Article foundArticle = articleListOpt.get();

            if(commentorOpt.isPresent()){
                Users commentor =commentorOpt.get();
                Comment comment = new Comment();
                comment.setText(commentdto.getCommentText());
                comment.setCreatedCommentAt(LocalDateTime.now());
                commentRepository.save(comment);

                List<Comment> commentList= commentor.getComments();
                commentList.add(comment);
                usersRepository.save(commentor);

                List<Comment> articleComments = foundArticle.getArticlecomments();
                articleComments.add(comment);
                articleRepository.save(foundArticle);

                serviceResponseHelper.setHasError(false);
                successResponse.put("message", "inserted successfully");
                serviceResponseHelper.setResponseMessage(successResponse);
                serviceResponseHelper.setContent("works");
                return serviceResponseHelper;
            }
            if(foundArticle==null){
                serviceResponseHelper.setHasError(true);
                errorResponse.put("message", "no such article exsist");
                serviceResponseHelper.setResponseMessage(errorResponse);
                serviceResponseHelper.setContent(null);
                return serviceResponseHelper;
            }
            else{
                serviceResponseHelper.setHasError(true);
                errorResponse.put("message", "no such user exsist");
                serviceResponseHelper.setResponseMessage(errorResponse);
                serviceResponseHelper.setContent(null);
                return serviceResponseHelper;
            }

        }
    }

    public List<Comment> findAllComments(){
        List<Comment> allComments = commentRepository.findAll();
        return allComments;
    }


    public String createCommentforArticle(CommentDto commentDto){
       // long articleId = commentDto.getArticleIdComment();
        Optional<Article> article = articleRepository.findById(62L);

        if(!article.isPresent()){
            return "article id isn't valid";
        }
        if(commentDto.getCommentText()==""){
            return "comment body couldn't be empty";
        }
        else{
            Comment comment = new Comment();
            comment.setText(commentDto.getCommentText());
            commentRepository.save(comment);
            if(comment.getId()!=0) {
                List<Comment> commentList = article.get().getArticlecomments();
                commentList.add(comment);
                articleRepository.save(article.get());
            }

        }
        return "inserted";

    }

    public void deleteComment(Long id){

     List<Article> articleList = articleRepository.findAll();
     for(Article a: articleList){
         for(Comment c1: a.getArticlecomments()){
             if(c1.getId()==id){
                 a.getArticlecomments().remove(c1);
                 break;
             }
         }
     }
     List<Users> usersList = usersRepository.findAll();
     for(Users u: usersList){
         for(Comment c2: u.getComments()){
             if(c2.getId()==id){
                 u.getComments().remove(c2);
                 break;
             }
         }
     }
     commentRepository.deleteById(id);
    }
}
