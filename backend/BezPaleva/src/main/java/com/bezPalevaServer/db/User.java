package com.bezPalevaServer.db;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String nickname;
    private double rating;
    private String photoPath;
    private String vkId;
    private int numberMarksPerDay;

    @OneToMany(mappedBy = "user")
    private List<Mark> marks;

    public User(){}

    public User( String firstName, String lastName,  String vkId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.vkId = vkId;
        numberMarksPerDay = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getVkId() {
        return vkId;
    }

    public void setVkId(String vkId) {
        this.vkId = vkId;
    }

    public int getNumberMarksPerDay() {
        return numberMarksPerDay;
    }

    public void incNumberMarksPerDay() {
        this.numberMarksPerDay++;
    }
}
