package com.baylor.se.project.bearnews.Service;

import com.baylor.se.project.bearnews.Controller.dto.CommentDto;
import com.baylor.se.project.bearnews.Models.Article;
import com.baylor.se.project.bearnews.Models.Comment;
import com.baylor.se.project.bearnews.Repository.ArticleRepository;
import com.baylor.se.project.bearnews.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ArticleRepository articleRepository;

    public String createComment(Comment comment){
        if(comment.getText()=="")
            return "comment body couldn't be empty";
        commentRepository.save(comment);
        return String.valueOf(comment.getId());
    }

    public List<Comment> findAllComments(){
        List<Comment> allComments = commentRepository.findAll();
        return allComments;
    }


    public String createCommentforArticle(CommentDto commentDto){
        long articleId = commentDto.getArticleIdComment();
        Optional<Article> article = articleRepository.findById(articleId);

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

    public String deleteComment(Long id){
        articleRepository.deleteById(id);
        return "deleted successfully";
    }
}
