package com.project.Jaskirat.services;
import com.project.Jaskirat.entities.User;
import com.project.Jaskirat.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    //Create
    public User saveUser(User user){
        return repository.save(user);
    }
    public List<User> saveUsers(List<User> users){
        return repository.saveAll(users);
    }

    //Get
    public List<User> getUsers(){
        return repository.findAll();
    }
    public User getUserById(int userID){
        return repository.findById(userID);
    }
    public User getUserByEmail(String email){
        return repository.findByEmail(email);
    }


    //Delete
    public String deleteUser(int id){
        repository.deleteById(id);
        return "deleted succesfully"+id;
    }

    //Update
    public User updateUser(User user){
        User existingUser=repository.findById(user.getId());
        existingUser.setName(user.getName());
        return repository.save(existingUser);
    }
}
