package com.project.Jaskirat.controller;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.Jaskirat.DateFormat;
import com.project.Jaskirat.entities.Comment;
import com.project.Jaskirat.services.CommentService;
import com.project.Jaskirat.services.UserService;
import com.project.Jaskirat.services.PostService;
import com.project.Jaskirat.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.Jaskirat.entities.Post;
import com.project.Jaskirat.exceptionHandler;
import java.util.*;

@RestController
public class PostController {
    @Autowired
    private PostService service;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @PostMapping("/post")
    public ResponseEntity<Object> makePost(@RequestBody Map<String, String> postDetails){
        int userId = Integer.parseInt(postDetails.get("userID"));
        if(userService.getUserById(userId)==null){
            return exceptionHandler.errorResponse("User does not exist");
        }
        else{
            Post newPost = new Post();
            newPost.setPostBody(postDetails.get("postBody"));
            newPost.setPostDate(new Date());
            newPost.setPostCreator(userService.getUserById(userId));
            service.savePost(newPost);
            return ResponseEntity.ok().body("Post created successfully");
        }
    }

    @GetMapping("/post")
    public ResponseEntity<Object> findPostById(@RequestParam int postID){
        Post op = service.getPostById(postID);
        if(op==null)
            return exceptionHandler.errorResponse("Post does not exist");
        else{
            Map<String, Object> details=new HashMap<>();
            details.put("postID", op.getPostId());
            details.put("postBody", op.getPostBody());
            details.put("date", DateFormat.formatData(op.getPostDate()));
            //comments
            List<Comment> comments = commentService.getCommentsByPost(op);
            System.out.println(comments.size());
            ArrayList<Map<String, Object>> commentsFormatted = new ArrayList<>();
            for (Comment comment : comments){
                Map<String, Object> commentMap= new HashMap<>();
                commentMap.put("commentID", comment.getCommentID());
                commentMap.put("commentBody", comment.getCommentBody());
                commentMap.put("commentCreator", Map.of("userID", comment.getCommentCreator().getId(),"name", comment.getCommentCreator().getName()));
                commentsFormatted.add(commentMap);
            }
            details.put("comments",commentsFormatted);
            return ResponseEntity.ok(details);
        }
    }


    @PatchMapping("/post")
    public ResponseEntity<Object> editPost(@RequestBody Map<String, String> postDetails){
        int postID=Integer.parseInt(postDetails.get("postID"));
        if(service.getPostById(postID)==null) {
            return exceptionHandler.errorResponse("Post does not exist");
        }
        else{
            Post op= service.getPostById(postID);
            op.setPostBody(postDetails.get("postBody"));
            service.savePost(op);
            return ResponseEntity.ok().body("Post edited successfully");
        }
    }

    @DeleteMapping("/post")
    public ResponseEntity<Object> deletePost(@RequestParam int postID){
        Post op= service.getPostById(postID);
        if(op==null){
            return exceptionHandler.errorResponse("Post does not exist");
        }
        else{
            List<Comment> comments=commentService.getCommentsByPost(op);
            for(Comment comment: comments){
                int commentID=comment.getCommentID();
                commentService.deleteCommentByCommentID(commentID);
            }
            service.deletePostByPostID(postID);
            return ResponseEntity.ok().body("Post deleted");
        }
    }
}
