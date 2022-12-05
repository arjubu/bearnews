package com.baylor.se.project.bearnews.Service;

import com.baylor.se.project.bearnews.Controller.ServiceResponseHelper;
import com.baylor.se.project.bearnews.Controller.dto.UserInterestDto;
import com.baylor.se.project.bearnews.Controller.dto.UserProfileDto;
import com.baylor.se.project.bearnews.Models.Article;
import com.baylor.se.project.bearnews.Models.Tag;
import com.baylor.se.project.bearnews.Models.UserType;
import com.baylor.se.project.bearnews.Models.Users;
import com.baylor.se.project.bearnews.Repository.TagRepository;
import com.baylor.se.project.bearnews.Repository.UsersRepository;
import com.baylor.se.project.bearnews.ResponseObjectMappers.ArticleByUsersObjectMapper;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class UsersService {

    @Value("${redis.server}")
    private String redisServer;

    @Value("${redis.port}")
    private String redisPort;

    @Value("${redis.password}")
    private String redisPassword;
    @Autowired
    UsersRepository userRepo;

    @Autowired
    TagService tagService;

//    @Autowired
//    ArticleRepository articleRepository;

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
                        String passwordHash = Hashing.sha256().hashString(user.getPassword(), StandardCharsets.UTF_8).toString();
                        user.setPassword(passwordHash);

                        newUser = userRepo.save(newUser);
                        DefaultJedisClientConfig defaultJedisClientConfig = DefaultJedisClientConfig.builder().password(redisPassword).build();
                        Jedis jedis = new Jedis(redisServer, Integer.valueOf(redisPort),defaultJedisClientConfig);
                        String OTP = RandomStringUtils.randomNumeric(6);
                        jedis.setex(user.getEmail(), 300, OTP);
                        EmailSenderService emailSenderService = new EmailSenderService();
                        emailSenderService.zohoSendMail(user.getEmail(),OTP);
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
        Optional<Users> userInSystem = userRepo.findByEmail(email);
        if(!userInSystem.isPresent())
            return false;
        else
            return true;
    }

    public ServiceResponseHelper interestListAttach(UserInterestDto userInterestDto){
        Map errorResponse = new HashMap<>();
        Map successResponse = new HashMap<>();
        ServiceResponseHelper serviceResponseHelper = new ServiceResponseHelper(false,null,null);

       Optional<Users>usersQueryOpt = userRepo.findByEmail(userInterestDto.getUsername());
       if(usersQueryOpt.isPresent()) {
           Users usersQuery = usersQueryOpt.get();
           String interests = userInterestDto.getTagsContaining();
           ArrayList<String> iL = new ArrayList<>(Arrays.asList(interests.split(",")));
           if(iL.size()>0){
               List<Tag> isLikedTags = new ArrayList<>();
               for(String s: iL){
                   Tag queryTag = tagService.findTagsByText(s);
                   if(queryTag!=null) {
                       usersQuery.getIsLiked().add(queryTag);
                   }
                     //usersQuery.setIsLiked();
               }
               //usersQuery.setIsLiked(isLikedTags);
               userRepo.save(usersQuery);
               serviceResponseHelper.setHasError(false);
               successResponse.put("message", "updated successfully");
               serviceResponseHelper.setResponseMessage(successResponse);
               serviceResponseHelper.setContent(null);

           }
           else{
               serviceResponseHelper.setHasError(true);
               errorResponse.put("message", "give valid tags");
               serviceResponseHelper.setResponseMessage(errorResponse);
               serviceResponseHelper.setContent(null);
               return serviceResponseHelper;
           }
       }
       else {
           serviceResponseHelper.setHasError(true);
           errorResponse.put("message", "No such users exsists");
           serviceResponseHelper.setResponseMessage(errorResponse);
           serviceResponseHelper.setContent(null);
           return serviceResponseHelper;
       }
        return serviceResponseHelper;


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

        DefaultJedisClientConfig defaultJedisClientConfig = DefaultJedisClientConfig.builder().password(redisPassword).build();
        Jedis jedis = new Jedis(redisServer, Integer.valueOf(redisPort),defaultJedisClientConfig);
        String otp = jedis.get(requestBody.get("email"));

        if(otp != null){
            if(otp.equals(requestBody.get("otp"))){
                Optional<Users> users = userRepo.findByEmail(requestBody.get("email"));
                users.get().setActive(true);
                userRepo.save(users.get());
                serviceResponseHelper.setHasError(false);
                successResponse.put("message", "OTP Validate Successfully!");
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

                   responseArticle.setUsersCreatorId(allArticlesByAuthor.getId()); 

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

    public void usersArticleAttach(long usersId, Article articleSent){
        Users usersUpdate = foundUserById(usersId);
        List<Article> listToInsert = usersUpdate.getArticles();
        listToInsert.add(articleSent);
        userRepo.save(usersUpdate);

    }
    public List<Users> findingUsersCreatedArticles(){
        List<Users> articlesCreated = userRepo.findByArticlesIsNotNull();
        for(Users us: articlesCreated){
            for(Article a: us.getArticles()){
                System.out.println(a.getTitle());
            }
        }
        return articlesCreated;
    }

    public List<Users> findingArticlesByUser(Long id){
        List<Users> as = userRepo.findByArticlesIsNotNullAndArticlesIdEquals(id);
        return as;
    }

    public  ServiceResponseHelper validateLogin(Map<String,String> requestBody){
        Map errorResponse = new HashMap<>();
        Map successResponse = new HashMap<>();
        ServiceResponseHelper serviceResponseHelper = new ServiceResponseHelper(false,null,null);

        Optional<Users> users = userRepo.findByEmail(requestBody.get("email"));
        if(users.isPresent()){
            if(users.get().isActive()){
                if(users.get().getPassword().equals(Hashing.sha256().hashString(requestBody.get("password"), StandardCharsets.UTF_8).toString())){
                    serviceResponseHelper.setHasError(false);
                    successResponse.put("message", "login successful");
                    serviceResponseHelper.setResponseMessage(successResponse);
                    serviceResponseHelper.setContent(null);
                    return serviceResponseHelper;
                }else{
                    serviceResponseHelper.setHasError(true);
                    errorResponse.put("message", "Password is incorrect!");
                    serviceResponseHelper.setResponseMessage(errorResponse);
                    serviceResponseHelper.setContent(null);
                    return serviceResponseHelper;
                }
            }else{
                serviceResponseHelper.setHasError(true);
                errorResponse.put("message", "Email Validation Pending!");
                serviceResponseHelper.setResponseMessage(errorResponse);
                serviceResponseHelper.setContent(null);
                return serviceResponseHelper;
            }
        }else{
            serviceResponseHelper.setHasError(true);
            errorResponse.put("message", "No User Found!");
            serviceResponseHelper.setResponseMessage(errorResponse);
            serviceResponseHelper.setContent(null);
            return serviceResponseHelper;
        }
    }

    public ServiceResponseHelper findUserProfile(String userEmail){

        Map errorResponse = new HashMap<>();
        Map successResponse = new HashMap<>();
        ServiceResponseHelper serviceResponseHelper = new ServiceResponseHelper(false,null,null);

        Optional<Users> userQueryOpt = userRepo.findByEmail(userEmail);
        if(userQueryOpt.isPresent()){
            UserProfileDto userProfileDto = new UserProfileDto();
            Users userQuery = userQueryOpt.get();

            userProfileDto.setReqUserEmail(userQuery.getEmail());
            userProfileDto.setReqName(userQuery.getFirstName()+" "+userQuery.getLastName());
            //userProfileDto.setfavouriteArticleTitle(userQuery.getFavouriteArticleTitle());
            if(userQuery.getSocialMediaLink()==null)
                userProfileDto.setReqSocialLink("no url found");
            if(userQuery.getIsLiked().isEmpty()==false){
                List<Tag> interestTags = userQuery.getIsLiked();
                List<String> interestTagsToShow = new ArrayList<>();
                for(Tag t: interestTags){
                    interestTagsToShow.add(t.getTagText());
                }
                userProfileDto.setReqInterestList(interestTagsToShow);
            }
//            if(userQuery.getlikedArticleId()!=null){
//                Optional<Article> articleOptional = articleService.fetchArticle(userQuery.getlikedArticleId());
//                if(articleOptional.isPresent()){
//                    userProfileDto.setfavouritesArticleId(articleOptional.get().getId());
//                    userProfileDto.setfavouriteArticleTitle(articleOptional.get().getTitle());
//                }
//            }
            else{
                List<String> interestTagsToShow = new ArrayList<>();
                interestTagsToShow.add("select tags to interest list");
                userProfileDto.setReqInterestList(interestTagsToShow);
            }
            serviceResponseHelper.setHasError(false);
            successResponse.put("message", "desired contents found");
            serviceResponseHelper.setResponseMessage(successResponse);
            serviceResponseHelper.setContent(userProfileDto);
            return serviceResponseHelper;

        }
        else{
            serviceResponseHelper.setHasError(true);
            errorResponse.put("message", "No such user is there");
            serviceResponseHelper.setResponseMessage(errorResponse);
            serviceResponseHelper.setContent(null);
            return serviceResponseHelper;
        }

    }
    public ServiceResponseHelper resetPasswordSendOtp(String email) {

        Map errorResponse = new HashMap<>();
        Map successResponse = new HashMap<>();
        ServiceResponseHelper serviceResponseHelper = new ServiceResponseHelper(false, null, null);

        Optional<Users> userQueryOpt = userRepo.findByEmail(email);
        if (userQueryOpt.isPresent()) {
            DefaultJedisClientConfig defaultJedisClientConfig = DefaultJedisClientConfig.builder().password(redisPassword).build();
            Jedis jedis = new Jedis(redisServer, Integer.valueOf(redisPort), defaultJedisClientConfig);
            String OTP = RandomStringUtils.randomNumeric(6);
            jedis.setex(email, 300, OTP);
            EmailSenderService emailSenderService = new EmailSenderService();
            emailSenderService.zohoSendMail(email, OTP);

            serviceResponseHelper.setHasError(false);
            successResponse.put("message", "desired contents found");
            serviceResponseHelper.setResponseMessage(successResponse);
            serviceResponseHelper.setContent(OTP);
            return serviceResponseHelper;

        } else {
            serviceResponseHelper.setHasError(true);
            errorResponse.put("message", "No such user is there");
            serviceResponseHelper.setResponseMessage(errorResponse);
            serviceResponseHelper.setContent(null);
            return serviceResponseHelper;

        }
    }
        public ServiceResponseHelper displayUserTagsLiked(String userEmail){
        Map errorResponse = new HashMap<>();
        Map successResponse = new HashMap<>();
        ServiceResponseHelper serviceResponseHelper = new ServiceResponseHelper(false,null,null);

        Optional<Users> userQueryOpt = userRepo.findByEmail(userEmail);
        if(userQueryOpt.isPresent()){
            Users userQuery = userQueryOpt.get();
            List<String> tagsLiked = new ArrayList<>();
               if(userQuery.getIsLiked().isEmpty()==false){
                   for(Tag t: userQuery.getIsLiked()){
                       tagsLiked.add(t.getTagText());
                   }
                   serviceResponseHelper.setHasError(false);
                   successResponse.put("message", "No Found Tags");
                   serviceResponseHelper.setResponseMessage(successResponse);
                   serviceResponseHelper.setContent(tagsLiked);
                   return serviceResponseHelper;
               }

            else{
                serviceResponseHelper.setHasError(false);
                successResponse.put("message", "No Interest list found");
                serviceResponseHelper.setResponseMessage(successResponse);
                serviceResponseHelper.setContent(tagsLiked);
                return serviceResponseHelper;
            }
        }
        else {
            serviceResponseHelper.setHasError(true);
            errorResponse.put("message", "No such user is there");
            serviceResponseHelper.setResponseMessage(errorResponse);
            serviceResponseHelper.setContent(null);
            return serviceResponseHelper;
        }

    }

    public ServiceResponseHelper resetPassword(Map<String,String> requestBody){

        Map errorResponse = new HashMap<>();
        Map successResponse = new HashMap<>();
        ServiceResponseHelper serviceResponseHelper = new ServiceResponseHelper(false,null,null);

        Optional<Users> userQueryOpt = userRepo.findByEmail(requestBody.get("email"));
        if(userQueryOpt.isPresent()){
            if(requestBody.get("otp") == null){
                serviceResponseHelper.setHasError(true);
                errorResponse.put("message", "OTP is required!");
                serviceResponseHelper.setResponseMessage(errorResponse);
                serviceResponseHelper.setContent(null);
                return serviceResponseHelper;
            }
            if(!requestBody.get("newPassword").equals(requestBody.get("retypeNewPassword"))){
                serviceResponseHelper.setHasError(true);
                errorResponse.put("message", "New password and retyped password mismatched!");
                serviceResponseHelper.setResponseMessage(errorResponse);
                serviceResponseHelper.setContent(null);
                return serviceResponseHelper;
            }

            DefaultJedisClientConfig defaultJedisClientConfig = DefaultJedisClientConfig.builder().password(redisPassword).build();
            Jedis jedis = new Jedis(redisServer, Integer.valueOf(redisPort),defaultJedisClientConfig);
            String otp = jedis.get(requestBody.get("email"));

            if(otp == null || !otp.equals(requestBody.get("otp"))){
                serviceResponseHelper.setHasError(true);
                errorResponse.put("message", "OTP Mismatched or not found");
                serviceResponseHelper.setResponseMessage(errorResponse);
                serviceResponseHelper.setContent(null);
                return serviceResponseHelper;
            }
            String passwordHash = Hashing.sha256().hashString(requestBody.get("otp"), StandardCharsets.UTF_8).toString();
            userQueryOpt.get().setPassword(passwordHash);
            userRepo.save(userQueryOpt.get());

            serviceResponseHelper.setHasError(false);
            successResponse.put("message", "Password reset successful!");
            serviceResponseHelper.setResponseMessage(successResponse);
            serviceResponseHelper.setContent(userQueryOpt);
            return serviceResponseHelper;

        }else{
            serviceResponseHelper.setHasError(true);
            errorResponse.put("message", "No such user is there");
            serviceResponseHelper.setResponseMessage(errorResponse);
            serviceResponseHelper.setContent(null);
            return serviceResponseHelper;
        }
    }


}
