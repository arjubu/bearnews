package com.baylor.se.project.bearnews.Repository;

import com.baylor.se.project.bearnews.Models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByTagText(String name);
    Tag findTagsByTagText(String tagInterest);


}
