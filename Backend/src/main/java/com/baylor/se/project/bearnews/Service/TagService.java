package com.baylor.se.project.bearnews.Service;

import com.baylor.se.project.bearnews.Models.Tag;
import com.baylor.se.project.bearnews.Repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    TagRepository tagRepository;


    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
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

}
