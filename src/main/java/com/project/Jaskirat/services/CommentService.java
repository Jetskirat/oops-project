package com.project.Jaskirat.services;
import com.project.Jaskirat.entities.Comment;
import com.project.Jaskirat.entities.User;
import com.project.Jaskirat.entities.Post;
import com.project.Jaskirat.repositories.CommentRepository;
import com.project.Jaskirat.repositories.PostRepository;
import com.project.Jaskirat.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository repository;

    //Create
    public Comment saveComment(Comment comment){
        return repository.save(comment);
    }

    //Get
    public Comment getByCommentId(int commentID){
        return repository.getByCommentID(commentID);
    }
    public List<Comment> getCommentsByPost(Post post){
        return repository.findAllByCommentPost(post);
    }


    //Update
    public Comment updatePost(Comment comment){
        Comment existingComment=repository.getByCommentID(comment.getCommentID());
        existingComment.setCommentBody(comment.getCommentBody());
        return repository.save(existingComment);
    }

    //Delete
    @Transactional
    public void deleteCommentByCommentID(int commentID){
        repository.deleteCommentByCommentID(commentID);
    }

}
