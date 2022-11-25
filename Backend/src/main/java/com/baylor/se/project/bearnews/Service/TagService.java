package com.baylor.se.project.bearnews.Service;

import com.baylor.se.project.bearnews.Models.Tag;
import com.baylor.se.project.bearnews.Repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    for (Tag t: foundTags){
        System.out.println(t.getId());
     }
    return foundTags;
    }

}
