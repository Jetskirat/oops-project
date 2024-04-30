package com.project.Jaskirat.entities;
import jakarta.persistence.*;
import com.project.Jaskirat.entities.User;
import org.hibernate.annotations.GeneratedColumn;
import java.util.Date;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int postID;
    @Column
    private String postBody;
    @Column
    private Date postDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User postCreator;

    public void setPostId(int pID) {
        postID = pID;
    }
    public void setPostBody(String pBody) {
        postBody = pBody;
    }
    public void setPostDate(Date pDate) {
        postDate = pDate;
    }
    public void setPostCreator(User pCreator) {
        postCreator = pCreator;
    }


    public int getPostId() {
        return postID;
    }
    public String getPostBody() {
        return postBody;
    }
    public Date getPostDate() {
        return postDate;
    }
    public User getPostCreator() {
        return postCreator;
    }
}
