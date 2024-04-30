package com.project.Jaskirat.repositories;
import com.project.Jaskirat.entities.User;
import com.project.Jaskirat.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Post findByPostID(int postID);
    Post findByPostDate(Date postDate);
    void deletePostByPostID(int postID);
    List<Post> findAll();
}
