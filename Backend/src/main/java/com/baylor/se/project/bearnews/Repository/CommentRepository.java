package com.baylor.se.project.bearnews.Repository;

import com.baylor.se.project.bearnews.Models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
}
