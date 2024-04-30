package com.project.Jaskirat.entities;
import jakarta.persistence.*;
import com.project.Jaskirat.entities.User;
import com.project.Jaskirat.entities.Post;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column
    private int commentID;
    @Column
    private String commentBody;
    @Column
    private String commentDate;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User commentCreator;

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post commentPost;


    public void setCommentId(int commID) {
        commentID=commID;
    }
    public void setCommentBody(String commBody) {
        commentBody=commBody;
    }
    public void setCommentDate(String commDate) {
        commentDate = commDate;
    }
    public void setCommentCreator(User commCreator) {
        commentCreator = commCreator;
    }
    public void setCommentPost(Post commPost) {
        commentPost = commPost;
    }


    public int getCommentID() {
        return commentID;
    }
    public String getCommentBody() {
        return commentBody;
    }
    public String getCommentDate() {
        return commentDate;
    }
    public User getCommentCreator() {
        return commentCreator;
    }
    public Post getCommentPost() {
        return commentPost;
    }
}
