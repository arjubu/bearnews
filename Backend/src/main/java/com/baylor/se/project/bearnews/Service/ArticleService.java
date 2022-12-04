package com.baylor.se.project.bearnews.Service;

import com.baylor.se.project.bearnews.Controller.ServiceResponseHelper;
import com.baylor.se.project.bearnews.Controller.dto.ArticleCommentDto;
import com.baylor.se.project.bearnews.Controller.dto.ArticleDto;
import com.baylor.se.project.bearnews.Controller.dto.ArticleResponseDto;
import com.baylor.se.project.bearnews.Controller.dto.CommentUserDto;
import com.baylor.se.project.bearnews.Models.*;

import com.baylor.se.project.bearnews.Repository.ArticleRepository;
import com.baylor.se.project.bearnews.Repository.TagRepository;
import com.baylor.se.project.bearnews.Repository.UsersRepository;
import com.baylor.se.project.bearnews.ResponseObjectMappers.ArticleWithUsersObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UsersService usersService;

    @Autowired
    TagService tagService;

    @Autowired
    TagRepository tagRepository;


    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    public ServiceResponseHelper createArticle(ArticleDto articleDto){
        Article article = new Article();

        ServiceResponseHelper serviceResponseHelper = new ServiceResponseHelper(false,null,null);
        Map errorResponse = new HashMap<>();
        Map successResponse = new HashMap<>();

        String tagContainingText = articleDto.getTagContainingText();
        Tag tagsToAttach = tagRepository.findTagsByTagText(tagContainingText);
        String userSentEmail = articleDto.getCreatedUsersEmail();
        Optional<Users> usersQueryopt = usersRepository.findByEmail(userSentEmail);

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
        if(articleDto.getTagContainingText()==""){
            serviceResponseHelper.setHasError(true);
            errorResponse.put("message", "article must contain a tag");
            serviceResponseHelper.setResponseMessage(errorResponse);
            serviceResponseHelper.setContent(null);
            return serviceResponseHelper;
        }

        if(articleDto.getCreatedUsersEmail()==""){
            serviceResponseHelper.setHasError(true);
            errorResponse.put("message", "article must have a user");
            serviceResponseHelper.setResponseMessage(errorResponse);
            serviceResponseHelper.setContent(null);
            return serviceResponseHelper;
        }

        if(usersQueryopt.isPresent()==false){
            serviceResponseHelper.setHasError(true);
            errorResponse.put("message", "valid user email isn't provided");
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
            article.setContains(tagsToAttach);

            articleRepository.save(article);
            long articleId = article.getId();
            if(articleId!=0){
                Users usersWhoCreated = usersQueryopt.get();
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


    public void saveBaylorNews(List<Map> baylorNews) throws JsonProcessingException {
        List<Article> articles = new ArrayList<>();
        List<Integer> bearNewsIds = new ArrayList<>();
        Users users = usersRepository.findByEmail("baylornews@baylornews.com").get();



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

                List<Tag> tags1 = tagRepository.findByTagText("BAYLORNEWS");


                article.setContains(tags1.get(0));
                articles.add(article);
            }
        }
        users.setArticles(articles);
        usersRepository.save(users);
        for (Article a: articles){
            ServiceResponseHelper serviceResponseHelper = new ServiceResponseHelper(true,a,null);
            simpMessagingTemplate.convertAndSend("/topic/newPost",new ObjectMapper().writeValueAsString(serviceResponseHelper));
        }

    }

    public  void saveBaylorTweet(Map baylorTweet) throws JsonProcessingException {

        List<String> tags = (List)baylorTweet.get("hashTags");
        List<Tag> savedTags = new ArrayList<>();
        Users users = usersRepository.findByEmail("twitter@baylornews.com").get();

        for(String s : tags){
            Tag tag = new Tag();
            tag.setTagText(s);
            tag = tagService.createTag(tag);
            if(tag == null){
                List<Tag> tags1 = tagRepository.findByTagText(s);
                tag = tags1.size()>0 ? tags1.get(0) : null;
            }
            savedTags.add(tag);
        }

        Article article = new Article();
        article.setArticleType(ArticleType.TWITTER);
        article.setContent(baylorTweet.get("description").toString());
        article.setDetailLink(baylorTweet.get("detailLink") != null ? baylorTweet.get("detailLink").toString() : null);
        article.setThumbLink(baylorTweet.get("thumbLink") != null ? baylorTweet.get("thumbLink").toString() : null);
        if(savedTags.size()>0){
            article.setContains(savedTags.get(0));
        }else{
            List<Tag> tags1 = tagRepository.findByTagText("BEARFEED");
            article.setContains(tags1.get(0));
        }
        List<Article> articles = new ArrayList<>();
        articles.add(article);
        users.setArticles(articles);
        usersRepository.save(users);
        ServiceResponseHelper serviceResponseHelper = new ServiceResponseHelper(true,article,null);
        simpMessagingTemplate.convertAndSend("/topic/newPost",new ObjectMapper().writeValueAsString(serviceResponseHelper));

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

                List<Users> author= usersService.findingArticlesByUser(a.getId());
                if(author.isEmpty()==false) {
                    article.setIdOfCreator(author.get(0).getId());
                    article.setNameofCreator(author.get(0).getFirstName());
                }

                article.setIdOfTag(a.getContains().getId());
                article.setTextOfTag(a.getContains().getTagText());

                articleDetails.add(article);
            }
        }
        return articleDetails;

    }
    public ServiceResponseHelper getAllArticles(ArticleType articleType){
        ServiceResponseHelper serviceResponseHelper = new ServiceResponseHelper(false,null,null);
        Map errorResponse = new HashMap<>();
        Map successResponse = new HashMap<>();

        List<Article> articleList = articleRepository.findAll();
        List<ArticleWithUsersObjectMapper> articleDetails = new ArrayList<>();

        if(articleList.isEmpty()){
            serviceResponseHelper.setHasError(true);
            errorResponse.put("message", "no article");
            serviceResponseHelper.setResponseMessage(errorResponse);
            serviceResponseHelper.setContent(null);
        }

        else{
            for (Article a : articleList) {
                if (a.getArticleType().toString().equals(articleType.toString())) {
                    ArticleWithUsersObjectMapper article = new ArticleWithUsersObjectMapper();
                    article.setTitleOfArticle(a.getTitle());
                    article.setContentOfArticle(a.getContent());
                    article.setIdOfArticle(a.getId());
                    article.setTimeOfArticleCretion(a.getCreatedAt());

                    List<Users> author = usersService.findingArticlesByUser(a.getId());
                    if (author.isEmpty() == false) {
                        article.setIdOfCreator(author.get(0).getId());
                        article.setNameofCreator(author.get(0).getFirstName()+" "+author.get(0).getLastName());
                    }else{
                        article.setIdOfCreator(0L);
                        article.setNameofCreator(articleType.toString());
                    }
                    article.setIdOfTag(a.getContains() == null ?  0L : a.getContains().getId() );
                    article.setTextOfTag(a.getContains() == null ? ""  : a.getContains().getTagText());
                    article.setDetailLink(a.getDetailLink() != null ? a.getDetailLink() : null);
                    article.setThumbLink(a.getThumbLink() != null ? a.getThumbLink() : null);
                    articleDetails.add(article);

                }
            }
            serviceResponseHelper.setHasError(false);
            successResponse.put("message", "login successful");
            serviceResponseHelper.setResponseMessage(successResponse);
            serviceResponseHelper.setContent(articleDetails);
        }
        return serviceResponseHelper;

    }
    public List<Article> getAll(){
        return   articleRepository.findAll();
    }

    public List<Long> getAllTitles(){
        Long titles;
        List<Article> articleList = articleRepository.findAll();
        List<Long> titlesList =  new ArrayList<>();
        if(articleList.isEmpty()){
            return null;
        }
        else{
            for(Article a: articleList){
                if(a.getArticleType().toString().equals("SYSTEM")) {
                    titles = a.getId();
                    titlesList.add(titles);
                }
            }
        }
        return titlesList;
    }

    public Article getArticlesByTitle(String titleSent){
        List<Article> foundArticle = articleRepository.findArticlesByTitle(titleSent);
        Article sentTitleArticle = new Article();
        if(foundArticle.isEmpty()==false) {
            System.out.println(foundArticle.get(0).getId());
            if(foundArticle.get(0).getArticleType().toString().equals("SYSTEM")) {
                sentTitleArticle=foundArticle.get(0);
            }
        }
        return sentTitleArticle;
    }
    public ServiceResponseHelper findArticleById(long articleId){
        Optional<Article> queryArticleOpt = articleRepository.findById(articleId);
        Article queryArticle = new Article();
        ArticleResponseDto sentArticleResp = new ArticleResponseDto();

        ServiceResponseHelper serviceResponseHelper = new ServiceResponseHelper(false,null,null);
        Map errorResponse = new HashMap<>();
        Map successResponse = new HashMap<>();

        if(queryArticleOpt.isPresent()){
            queryArticle = queryArticleOpt.get();
            List<Users> authorQuery = usersService.findingArticlesByUser(queryArticle.getId());
            if(authorQuery.isEmpty()==false){

                sentArticleResp.setIdOfCreator(authorQuery.get(0).getId());
                sentArticleResp.setFirstNameofCreator(authorQuery.get(0).getFirstName());
                sentArticleResp.setLastNameofCreator(authorQuery.get(0).getLastName());

                sentArticleResp.setIdOfArticle(queryArticle.getId());
                sentArticleResp.setTitleOfArticle(queryArticle.getTitle());
                sentArticleResp.setContentOfArticle(queryArticle.getContent());

                sentArticleResp.setIdOfTag(queryArticle.getContains().getId());
                sentArticleResp.setTextOfTag(queryArticle.getContains().getTagText());

                sentArticleResp.setTimeOfCreation(queryArticle.getCreatedAt());

                serviceResponseHelper.setHasError(false);
                successResponse.put("message", "article found in this id");
                serviceResponseHelper.setResponseMessage(successResponse);
                serviceResponseHelper.setContent(sentArticleResp);
                return serviceResponseHelper;

            }
            else{
                serviceResponseHelper.setHasError(true);
                errorResponse.put("message", "article author is empty");
                serviceResponseHelper.setResponseMessage(errorResponse);
                serviceResponseHelper.setContent(null);
                return serviceResponseHelper;
            }
        }
        else{
            serviceResponseHelper.setHasError(true);
            errorResponse.put("message", "article id doesn't exsist");
            serviceResponseHelper.setResponseMessage(errorResponse);
            serviceResponseHelper.setContent(null);
            return serviceResponseHelper;
        }

    }

    public ServiceResponseHelper getArticleComment(Long id){
        Map errorResponse = new HashMap<>();
        Map successResponse = new HashMap<>();
        ServiceResponseHelper serviceResponseHelper = new ServiceResponseHelper(false,null,null);
        Optional<Article> article = articleRepository.findById(id);
        if(article.isPresent()){
            ArticleCommentDto articleCommentDto = new ArticleCommentDto();
            articleCommentDto.setId(article.get().getId());
            articleCommentDto.setTitle(article.get().getTitle());
            articleCommentDto.setContent(article.get().getContent());
            articleCommentDto.setContains(article.get().getContains());
            articleCommentDto.setDetailLink(article.get().getDetailLink());
            articleCommentDto.setThumbLink(article.get().getThumbLink());
            articleCommentDto.setArticleType(article.get().getArticleType());
            articleCommentDto.setBaylorNewsId(articleCommentDto.getBaylorNewsId());
            articleCommentDto.setCreatedAt(article.get().getCreatedAt());
            articleCommentDto.setUpdatedAt(article.get().getUpdatedAt());

            List<CommentUserDto> commentUsers = new ArrayList<>();
            for(Comment c: article.get().getArticleComments()){
                List<Users> users = usersRepository.findByCommentsIsNotNullAndCommentsIdEquals(c.getId());
                CommentUserDto commentUserDto = new CommentUserDto();
                commentUserDto.setId(c.getId());
                commentUserDto.setText(c.getText());
                commentUserDto.setCreatedComment(c.getCreatedComment());
                commentUserDto.setUpdatedComment(c.getUpdatedComment());
                commentUserDto.setUser( users.size()>0 ? users.get(0).getFirstName()+" "+users.get(0).getLastName() : null);
                commentUsers.add(commentUserDto);
                //commentUserDto.set

            }
            articleCommentDto.setCommentUsers(commentUsers);
            serviceResponseHelper.setHasError(false);
            successResponse.put("message", "Article comments!");
            serviceResponseHelper.setResponseMessage(successResponse);
            serviceResponseHelper.setContent(articleCommentDto);
            return serviceResponseHelper;
        }else{
            serviceResponseHelper.setHasError(true);
            errorResponse.put("message", "No article found");
            serviceResponseHelper.setResponseMessage(errorResponse);
            serviceResponseHelper.setContent(null);
            return serviceResponseHelper;
        }
    }
}
