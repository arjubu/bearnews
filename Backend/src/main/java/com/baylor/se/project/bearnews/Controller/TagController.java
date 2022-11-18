package com.baylor.se.project.bearnews.Controller;


import com.baylor.se.project.bearnews.Models.Tag;
import com.baylor.se.project.bearnews.Repository.TagRepository;
import com.baylor.se.project.bearnews.Service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class TagController {

    @Autowired
    TagService tagService;

    @Autowired
    TagRepository tagRepository;


    @PostMapping("/createTag")
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
        Tag updated = tagService.createTag(tag);
        return new ResponseEntity<>(updated, new HttpHeaders(), HttpStatus.OK);
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

}
