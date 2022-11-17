package com.baylor.se.project.bearnews.Controller;


import com.baylor.se.project.bearnews.Models.Tag;
import com.baylor.se.project.bearnews.Service.TagService;
import com.baylor.se.project.bearnews.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TagController {

    @Autowired
    TagService tagService;


    @PostMapping("/createTag")
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
        Tag updated = tagService.createTag(tag);
        return new ResponseEntity<>(updated, new HttpHeaders(), HttpStatus.OK);
    }


    @RequestMapping(value = "/alltag", method = RequestMethod.GET)
    public ResponseEntity<Tag> getTags() {
        return new ResponseEntity(tagService.getAllTag(), HttpStatus.OK);

    }
}
