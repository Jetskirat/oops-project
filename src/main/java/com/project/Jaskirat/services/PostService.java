package com.project.Jaskirat.services;
import com.project.Jaskirat.entities.User;
import com.project.Jaskirat.entities.Post;
import com.project.Jaskirat.repositories.PostRepository;
import com.project.Jaskirat.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository repository;

    public PostService(PostRepository postRepository){
        this.repository = postRepository;
    }

    //Create
    public Post savePost(Post post){
        return repository.save(post);
    }

    //Get
    public Post getPostById(int postID){
        return repository.findByPostID(postID);
    }
    public List<Post> getPosts(){
        return repository.findAll();
    }


    //Update
    public Post updatePost(Post post){
        Post existingPost=repository.findByPostID(post.getPostId());
        existingPost.setPostBody(post.getPostBody());
        return repository.save(existingPost);
    }

    //Delete
    @Transactional
    public void deletePostByPostID(int postID){
        repository.deletePostByPostID(postID);
    }
}
