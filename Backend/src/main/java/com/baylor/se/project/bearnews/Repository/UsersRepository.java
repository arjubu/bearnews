package com.baylor.se.project.bearnews.Repository;

import com.baylor.se.project.bearnews.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {

    Optional<Users> findByEmail(String email);

    List<Users> findByArticlesIsNotNull();

    List<Users> findByArticlesIsNotNullAndArticlesIdEquals(long id);

    List<Users> findByCommentsIsNotNullAndCommentsIdEquals(long id);
}
