package jpa.buddy.blog.app.repositories;

import jpa.buddy.blog.app.entities.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select distinct p from Post p " +
           "where p.title = ?1 " +
           "order by p.lastUpdated")
    List<Post> findDistinctByTitle(String title, Pageable pageable);

    List<Post> findByTitle(String name);
}
