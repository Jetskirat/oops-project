package com.project.Jaskirat.repositories;
import com.project.Jaskirat.entities.Comment;
import com.project.Jaskirat.entities.User;
import com.project.Jaskirat.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>{
    Comment save(Comment comment);
    Comment getByCommentID(int commentID);
    void deleteCommentByCommentID(int commentID);
    List<Comment> findAllByCommentPost(Post post);

}
