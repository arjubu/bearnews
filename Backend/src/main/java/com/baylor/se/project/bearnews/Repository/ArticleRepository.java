package com.baylor.se.project.bearnews.Repository;

import com.baylor.se.project.bearnews.Models.Article;
import com.baylor.se.project.bearnews.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {

     Optional<Article> findByBaylorNewsId(Integer baylorNewsId);
}
