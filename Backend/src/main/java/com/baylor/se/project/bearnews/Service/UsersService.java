package com.baylor.se.project.bearnews.Service;

import com.baylor.se.project.bearnews.Controller.ServiceResponseHelper;
import com.baylor.se.project.bearnews.Models.Article;
import com.baylor.se.project.bearnews.Models.Tag;
import com.baylor.se.project.bearnews.Models.UserType;
import com.baylor.se.project.bearnews.Models.Users;
import com.baylor.se.project.bearnews.Repository.TagRepository;
import com.baylor.se.project.bearnews.Repository.UsersRepository;
import com.baylor.se.project.bearnews.ResponseObjectMappers.ArticleByUsersObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class UsersService {

    @Value("${redis.server}")
    private String redisServer;

    @Value("${redis.port}")
    private String redisPort;
    @Autowired
    UsersRepository userRepo;

    @Autowired
    TagService tagService;



    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }
    public ServiceResponseHelper RegisterUserToSystem(Users user){
        Map errorResponse = new HashMap<>();
        Map successResponse = new HashMap<>();

        ServiceResponseHelper serviceResponseHelper = new ServiceResponseHelper(false,null,null);


        Users newUser = user;
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if((patternMatches(user.getEmail(),regexPattern)) ==false) {
            serviceResponseHelper.setHasError(true);
            errorResponse.put("message", "Invalid Email Address!");
            serviceResponseHelper.setResponseMessage(errorResponse);
            serviceResponseHelper.setContent(null);
            return serviceResponseHelper;
        }
        else{
            if(user.getFirstName().equals("")){
                serviceResponseHelper.setHasError(true);
                errorResponse.put("message", "First Name Can Not Be Null");
                serviceResponseHelper.setResponseMessage(errorResponse);
                serviceResponseHelper.setContent(null);
                return serviceResponseHelper;
            }
            else {
                if(user.getLastName().equals("")){
                    serviceResponseHelper.setHasError(true);
                    errorResponse.put("message", "last name cannot be null");
                    serviceResponseHelper.setResponseMessage(errorResponse);
                    serviceResponseHelper.setContent(null);
                    return serviceResponseHelper;
                }
                else {
                    if(userExsistence(user.getEmail())==true){
                        serviceResponseHelper.setHasError(true);
                        errorResponse.put("message", "the email already exists in the system");
                        serviceResponseHelper.setResponseMessage(errorResponse);
                        serviceResponseHelper.setContent(null);
                        return serviceResponseHelper;
                    }
                    else {
                        user.setUserType(UserType.SystemUser);
                        newUser = userRepo.save(newUser);

                        Jedis jedis = new Jedis(redisServer, Integer.valueOf(redisPort));
                        jedis.setex(user.getEmail(), 300, RandomStringUtils.randomNumeric(6));
                        //System.out.println(jedis.get(user.getEmail()));
                        serviceResponseHelper.setHasError(false);
                        successResponse.put("message", "User created Successfully!");
                        serviceResponseHelper.setResponseMessage(successResponse);
                        serviceResponseHelper.setContent(newUser);
                        return serviceResponseHelper;
                    }
                }
            }
        }
    }

    public List<Users> listAllUser(){
        List<Users> allUserRegistered = userRepo.findAll();
        return allUserRegistered;
    }
    public boolean userExsistence(String email){
        List<Users> userInSystem = userRepo.findByEmail(email);
        if(userInSystem.isEmpty())
            return false;
        else
            return true;
    }

    public Users interestListAttach(List<String> interestLists,Long id){
        Users usersToUpdate = new Users();
        List<Tag> tagsToAttach = tagService.ListOfTagsFound(interestLists);
        Optional<Users> userQueryOpt = userRepo.findById(id);
        if(userQueryOpt.isPresent()){
            usersToUpdate = userQueryOpt.get();
            usersToUpdate.setIsLiked(tagsToAttach);
            userRepo.save(usersToUpdate);
        }
        return usersToUpdate;

    }
    public Users foundUserById(Long id){
        Optional<Users> userQueryOpt = userRepo.findById(id);
        if(userQueryOpt.isPresent()){
           return userQueryOpt.get();
        }
        else
            return null;
    }

    public ServiceResponseHelper validateOtp(Map<String,String> requestBody){
        Map errorResponse = new HashMap<>();
        Map successResponse = new HashMap<>();

        ServiceResponseHelper serviceResponseHelper = new ServiceResponseHelper(false,null,null);

        Jedis jedis = new Jedis(redisServer, Integer.valueOf(redisPort));
        String otp = jedis.get(requestBody.get("email"));

        if(otp != null){
            if(otp.equals(requestBody.get("otp"))){
                serviceResponseHelper.setHasError(false);
                successResponse.put("message", "OTP");
                serviceResponseHelper.setResponseMessage(successResponse);
                serviceResponseHelper.setContent(otp);
                return serviceResponseHelper;

            }else{
                serviceResponseHelper.setHasError(true);
                errorResponse.put("message", "OTP Mismatched!");
                serviceResponseHelper.setResponseMessage(errorResponse);
                serviceResponseHelper.setContent(null);
                return serviceResponseHelper;
            }

        }else {
            serviceResponseHelper.setHasError(true);
            errorResponse.put("message", "No OTP Found!");
            serviceResponseHelper.setResponseMessage(errorResponse);
            serviceResponseHelper.setContent(null);
            return serviceResponseHelper;
        }



    }
    public ServiceResponseHelper deleteUSer(Long id){
        Map errorResponse = new HashMap<>();
        Map successResponse = new HashMap<>();

        ServiceResponseHelper serviceResponseHelper = new ServiceResponseHelper(false,null,null);
        if(foundUserById(id)==null) {
            serviceResponseHelper.setHasError(true);
            errorResponse.put("message", "no such user exsist in this id");
            serviceResponseHelper.setResponseMessage(errorResponse);
            serviceResponseHelper.setContent(null);
            return serviceResponseHelper;
        }
        else{
            userRepo.deleteById(id);
            serviceResponseHelper.setHasError(false);
            successResponse.put("message", "deleted successfully");
            serviceResponseHelper.setResponseMessage(successResponse);
            serviceResponseHelper.setContent("user deleted");
            return serviceResponseHelper;
        }

    }
    public List<ArticleByUsersObjectMapper> findArticlesByUsers(Long id){
        Users allArticlesByAuthor = foundUserById(id);
        List<ArticleByUsersObjectMapper> returnedArticles = new ArrayList<>();
        if(allArticlesByAuthor!=null){
            if(allArticlesByAuthor.getArticles().isEmpty()==false) {
               for(Article a: allArticlesByAuthor.getArticles()){
                   ArticleByUsersObjectMapper responseArticle = new ArticleByUsersObjectMapper();
                   responseArticle.setArticleId(a.getId());
                   responseArticle.setUsersCreatorId(a.getCreatedBy().getId());
                   responseArticle.setArticleTitle(a.getTitle());
                   responseArticle.setArticleContent(a.getContent());
                   responseArticle.setArticleTagId(a.getContains().getId());
                   responseArticle.setArticleTagText(a.getContains().getTagText());
                   responseArticle.setArticleCreationTime(a.getCreatedAt());
                   returnedArticles.add(responseArticle);
               }
              // System.out.println(responseArticle);
            }
        }
        return returnedArticles;
    }


}
