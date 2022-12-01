package com.baylor.se.project.bearnews.Controller;


import com.baylor.se.project.bearnews.Models.Tag;
import com.baylor.se.project.bearnews.Repository.TagRepository;
import com.baylor.se.project.bearnews.Service.TagService;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
public class TagController {

    @Autowired
    TagService tagService;

    @Autowired
    TagRepository tagRepository;


    @PostMapping("/createTag")
    public ResponseEntity<?> createTag(@RequestBody Tag tag) {
        Tag updated = tagService.createTag(tag);
        if(updated!=null)
        return new ResponseEntity<>(updated, new HttpHeaders(), HttpStatus.OK);

        return new ResponseEntity<>("cannot create already exsists", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/alltag", method = RequestMethod.GET)
    public ResponseEntity<Tag> getTags() {
        return new ResponseEntity(tagService.getAllTag(), HttpStatus.OK);

    }

    @GetMapping("/tags/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable("id") Long id) {
        Tag tag = tagService.getTagById(id);
        return new ResponseEntity<>(tag, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/getTagByLetter", method = RequestMethod.GET)
    public ResponseEntity<?> getTagsByLetter(@RequestBody Map<String, String> sentLetter) throws JsonProcessingException {
        String letterToSearch = sentLetter.get("suggString").toLowerCase();


        ServiceResponseHelper serviceResponseHelper =  tagService.listOfTags(letterToSearch);;
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
}
