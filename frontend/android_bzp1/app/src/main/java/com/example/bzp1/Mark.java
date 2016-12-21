package com.example.bzp1;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Mark{

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("x")
    @Expose
    private double x;
    @SerializedName("y")
    @Expose
    private double y;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("photoPath")
    @Expose
    private String photoPath;
    @SerializedName("irrelevanceLevel")
    @Expose
    private int irrelevanceLevel;
    @SerializedName("deathTime")
    @Expose
    private long deathTime;
    @SerializedName("user")
    @Expose
    private int userId;

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public long getDeathTime() {
        return deathTime;
    }
    public int getId() {
        return id;
    }
    public int getIrrelevanceLevel() {
        return irrelevanceLevel;
    }
    public String getDescription() {
        return description;
    }
    public String getPhotoPath() {
        return photoPath;
    }
    public String getType() {
        return type;
    }
    public int getUserId(){
        return userId;
    }

    public void setDeathTime(long deathTime) {
        this.deathTime = deathTime;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setIrrelevanceLevel(int irrelevanceLevel) {
        this.irrelevanceLevel = irrelevanceLevel;
    }
    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
    public void setUserId(int userId){
        this.userId=userId;
    }


    public Mark(){

    }

    /*
    public Mark(double x, double y, String type, String description, int userId ){
        this.x=x;
        this.y=y;
        this.type=type;
        this.description=description;
        this.userId=userId;
    }
*/
    public Mark(int id, double x, double y, String type, String description , String photoPath, int irrelevanceLevel, int deathTime, int userId){
        this.x=x;
        this.y=y;
        this.type=type;
        this.description=description;
        this.photoPath=photoPath;
        this.irrelevanceLevel=irrelevanceLevel;
        this.deathTime=deathTime;
    }
}


