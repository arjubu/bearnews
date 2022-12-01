package com.baylor.se.project.bearnews.Service;

import com.baylor.se.project.bearnews.Controller.ServiceResponseHelper;
import com.baylor.se.project.bearnews.Models.Tag;
import com.baylor.se.project.bearnews.Repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class TagService {
    @Autowired
    TagRepository tagRepository;
    
    public Tag createTag(Tag tag) {
        if(!tag.getTagText().equals("")) {
            List<Tag> isTagExsisting = tagRepository.findByTagText(tag.getTagText().toLowerCase());
            if(isTagExsisting.isEmpty()) {
                tag.setTagText(tag.getTagText().toLowerCase());
                return tagRepository.save(tag);
            }
            else
                return null;
        }
        return null;
    }

    public List<Tag> getAllTag() {return tagRepository.findAll();}

    public Tag getTagById(Long id) {
        Optional<Tag> tag = tagRepository.findById(id);

        if (tag.isPresent()) {
            return tag.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No tag record exist for given id");
        }
    }


    

    public List<Tag> ListOfTagsFound(List<String> userInterests){
    List<Tag> foundTags = new ArrayList<>();
    for(String us: userInterests){
        Tag t1 = new Tag();
        t1=tagRepository.findTagsByTagText(us.toLowerCase());
        foundTags.add(t1);
     }
    return foundTags;
    }

    public Tag findTagByIdForArticle(Long id) {
        Optional<Tag> tag = tagRepository.findById(id);

        if (tag.isPresent()) {
            return tag.get();
        } else {
           return null;
        }
    }

    public ServiceResponseHelper listOfTags(String suggStr){
        Map errorResponse = new HashMap<>();
        Map successResponse = new HashMap<>();
        ServiceResponseHelper serviceResponseHelper = new ServiceResponseHelper(false,null,null);

        List<Tag> tagsToSuggest = tagRepository.findAll();
        List<String> tagsTosent = new ArrayList<>();
        for(Tag t: tagsToSuggest){
            if(t.getTagText().startsWith(suggStr)){
                tagsTosent.add(t.getTagText());
            }
        }
        if(tagsTosent.isEmpty()){
            serviceResponseHelper.setHasError(true);
            errorResponse.put("message", "no tags exsists with this letter");
            serviceResponseHelper.setResponseMessage(errorResponse);
            serviceResponseHelper.setContent(null);
            return serviceResponseHelper;
        }
        else{
            Collections.sort(tagsTosent);
            serviceResponseHelper.setHasError(false);
            successResponse.put("message", "tags found");
            serviceResponseHelper.setResponseMessage(successResponse);
            serviceResponseHelper.setContent(tagsTosent);
            return serviceResponseHelper;
        }
    }

}
