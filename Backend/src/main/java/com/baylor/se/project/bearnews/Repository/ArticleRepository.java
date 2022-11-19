package com.baylor.se.project.bearnews.Repository;

import com.baylor.se.project.bearnews.Models.Article;
import com.baylor.se.project.bearnews.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {
}
