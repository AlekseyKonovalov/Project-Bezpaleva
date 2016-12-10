package com.bezPalevaServer.db;

import javax.persistence.*;

import java.sql.Timestamp;

@Entity
public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double x;
    private double y;
    private String type;
    private String description;
    private String photoPath;
    private Timestamp deathTime;
    private int irrelevanceLevel;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getType() {
        return type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void incIrrelevanceLevel() {
        this.irrelevanceLevel++;
    }

    public String getDescription() {
        return description;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public Timestamp getDeathTime() {
        return deathTime;
    }

    public int getIrrelevanceLevel() {
        return irrelevanceLevel;
    }

    public Mark(){}

    public Mark(double x, double y, String type, String description,  long deathTime, User user) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.description = description;
        this.photoPath = null;
        this.deathTime = new Timestamp(deathTime);
        this.irrelevanceLevel = 0;
        this.user = user;
    }

    public long getUser() {
        return user.getId();
    }

    public void setUser(User user) {
        this.user = user;
    }
}
