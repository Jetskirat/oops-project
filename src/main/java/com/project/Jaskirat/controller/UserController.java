package com.project.Jaskirat.controller;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.Jaskirat.DateFormat;
import com.project.Jaskirat.entities.Comment;
import com.project.Jaskirat.entities.Post;
import com.project.Jaskirat.services.CommentService;
import com.project.Jaskirat.services.PostService;
import com.project.Jaskirat.services.UserService;
import com.project.Jaskirat.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.project.Jaskirat.exceptionHandler;
import java.util.Date;

@RestController
public class UserController{
    @Autowired
    private UserService service;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user){
        User username=service.getUserByEmail(user.getEmail());
        if(username==null){
            return exceptionHandler.errorResponse("User does not exist");
        }
        else{
            if(username.getPassword().equals(user.getPassword()))
                return ResponseEntity.ok().body("Login Successful");
            else{
                return exceptionHandler.errorResponse("Username/Password Incorrect");
            }
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody User user){
        if(service.getUserByEmail(user.getEmail())!=null)
            return exceptionHandler.errorResponse("Forbidden, Account already exists");
        else{
            service.saveUser(user);
            return ResponseEntity.ok().body("Account Creation Successful");
        }
    }

//    @PostMapping("/adduser")
//    public User addUser(@RequestBody User user){
//        return service.saveUser(user);
//    }
//    @PostMapping("/addusers")
//    public List<User> addUsers(@RequestBody List<User> users){
//        return service.saveUsers(users);
//    }

    @GetMapping("/")
    public ResponseEntity<Object> getUserFeed(){
        List<Post> posts=postService.getPosts();
        posts.sort((p1, p2) -> p2.getPostDate().compareTo(p1.getPostDate()));
        ArrayList<Map<String, Object>> details = new ArrayList<>();
        for(Post post: posts){
            Map<String, Object> allDetails=new HashMap<>();
            allDetails.put("postID", Integer.toString(post.getPostId()));
            allDetails.put("postBody", post.getPostBody());
            allDetails.put("date", DateFormat.formatData(post.getPostDate()));
            List<Comment> comments=commentService.getCommentsByPost(post);
            List<Map<String,Object>> commentsMap = new ArrayList<>();
            for(Comment comment: comments){
                Map<String,Object> comm=new HashMap<>();
                comm.put("commentID", Integer.toString(comment.getCommentID()));
                comm.put("commentBody", comment.getCommentBody());
                comm.put("commentCreator", Map.of("userID", comment.getCommentCreator().getId(),"name", comment.getCommentCreator().getName()));
                commentsMap.add(comm);
            }
            allDetails.put("comments",commentsMap);
            details.add(allDetails);
        }
        return ResponseEntity.ok().body(details);
    }

    @GetMapping("/user")
    public ResponseEntity<Object> findUserById(@RequestParam int userID){
        if(service.getUserById(userID)==null)
            return exceptionHandler.errorResponse("User does not exist");
        else{
            User username=service.getUserById(userID);
            Map<String, Object> details=new HashMap<>();
            details.put("name", username.getName());
            details.put("userID", username.getId());
            details.put("email",username.getEmail());
            return ResponseEntity.ok(details);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<Object> findUsers(){
        if(service.getUsers()==null)
            return null;
        else{
            List<User> usernames=service.getUsers();
            Map<String, Object>[] arr;
            arr=new Map[usernames.size()];
            int i=0;
            for(User username: usernames){
                Map<String, Object> details=new HashMap<>();
                details.put("name", username.getName());
                details.put("userID", username.getId());
                details.put("email", username.getEmail());
                arr[i]=details;
                i++;
            }
            return ResponseEntity.ok(arr);
        }
    }

//    @PutMapping("/update")
//    public User updateUser(@RequestBody User user){
//        return service.updateUser(user);
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public String deleteUser(@PathVariable int userID){
//        return service.deleteUser(userID);
//    }
}
