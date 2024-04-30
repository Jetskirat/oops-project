package com.project.Jaskirat.repositories;
import com.project.Jaskirat.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    User findById(int userID);
}
