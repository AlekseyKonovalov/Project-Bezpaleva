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
    private String photo_path;
    private Timestamp death_time;
    private int irrelevance_level;

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
        this.irrelevance_level++;
    }

    public String getDescription() {
        return description;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    public String getphoto_path() {
        return photo_path;
    }

    public Timestamp getDeathTime() {
        return death_time;
    }

    public int getirrelevanceLevel() {
        return irrelevance_level;
    }

    public Mark(){}

    public Mark(double x, double y, String type, String description,  long deathTime) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.description = description;
        this.photo_path = null;
        this.death_time = new Timestamp(deathTime);
        this.irrelevance_level = 0;
    }

}
