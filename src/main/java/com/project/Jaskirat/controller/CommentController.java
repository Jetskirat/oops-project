package com.project.Jaskirat.controller;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CommentController {
    @Autowired
    private CommentService service;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @PostMapping("/comment")
    public ResponseEntity<Object> makeComment(@RequestBody Map<String, String> commentDetails){
        int userID=(Integer.parseInt(commentDetails.get("userID")));
        int postID=(Integer.parseInt(commentDetails.get("postID")));
        User user = userService.getUserById(userID);
        if(user==null){
            return exceptionHandler.errorResponse("User does not exist");
        }
        else{
            if(postService.getPostById(postID)==null){
                return exceptionHandler.errorResponse("Post does not exist");
            }
            else{
                Comment newComment=new Comment();
                newComment.setCommentBody(commentDetails.get("commentBody"));
                newComment.setCommentCreator(user);
                newComment.setCommentPost(postService.getPostById(postID));
                service.saveComment(newComment);
                return ResponseEntity.ok().body("Comment created successfully");
            }
        }
    }

    @GetMapping("/comment")
    public ResponseEntity<Object> getComment(@RequestParam int commentID){
        if(service.getByCommentId(commentID)==null){
            return exceptionHandler.errorResponse("Comment does not exist");
        }
        else{
            Comment oc=service.getByCommentId(commentID);
            Map<String, Object> commentDetails=new HashMap<>();
            commentDetails.put("commentID", commentID);
            commentDetails.put("commentBody", oc.getCommentBody());
            User user = oc.getCommentCreator();
            Map<String, Object> userDetails = new HashMap<>();
            userDetails.put("userID", user.getId());
            userDetails.put("name", user.getName());

            commentDetails.put("commentCreator",userDetails);
            return ResponseEntity.ok(commentDetails);
        }
    }

    @PatchMapping("/comment")
    public ResponseEntity<Object> editComment(@RequestBody Map<String, String> commentDetails){
        int commentID=Integer.parseInt(commentDetails.get("commentID"));
        if(service.getByCommentId(commentID)==null) {
            return exceptionHandler.errorResponse("Comment does not exist");
        }
        else{
            Comment oc= service.getByCommentId(commentID);
            oc.setCommentBody(commentDetails.get("commentBody"));
            service.saveComment(oc);
            return ResponseEntity.ok().body("Comment edited successfully");
        }
    }

    @DeleteMapping("/comment")
    public ResponseEntity<Object> deleteComment(@RequestParam int commentID){
        if(service.getByCommentId(commentID)==null){
            return exceptionHandler.errorResponse("Comment does not exist");
        }
        else{
            service.deleteCommentByCommentID(commentID);
            return ResponseEntity.ok("Comment deleted");
        }
    }
}
