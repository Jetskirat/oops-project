package com.project.Jaskirat.entities;
import jakarta.persistence.*;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="user_id_seq")
    private int userID;

    @Column(unique = true)
    private String email;
    @Column
    private String name;
    @Column
    private String password;

    public int getId(){
        return userID;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public String getName(){
        return name;
    }
    public void setName(String nme){
        name=nme;
    }
    public void setPassword(String nme){
        password=nme;
    }
}
