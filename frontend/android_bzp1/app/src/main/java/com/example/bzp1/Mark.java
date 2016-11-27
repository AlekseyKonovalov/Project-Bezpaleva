package com.example.bzp1;


public class Mark{

    private int id;
    private double x;
    private double y;
    private String type;
    private String description;
    private String photoPath;
    private int irrelevanceLevel;
    private int deathTime;

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public int getDeathTime() {
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

    public void setDeathTime(int deathTime) {
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
}
