package com.example.bzp1;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("rating")
    @Expose
    private int rating;
    @SerializedName("photoPath")
    @Expose
    private String photoPath;
    @SerializedName("vkId")
    @Expose
    private int vkId;


    public int getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getNickname() {
        return nickname;
    }
    public String getLastName() {
        return lastName;
    }
    public int getRating() {
        return rating;
    }
    public String getPhotoPath() {
        return photoPath;
    }
    public int getVkId() {
        return vkId;
    }


    public void setId(int id) {
        this.id = id;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
    public void setVkId(int vkId) {
        this.vkId = vkId;
    }
}
