package com.bezPalevaServer.db;

import javax.persistence.*;
import java.awt.*;
import java.sql.Date;

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
    private Long death_time;
    private int relev_coeff;

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

    public String getDescription() {
        return description;
    }

    public String getphoto_path() {
        return photo_path;
    }

    public Long getDeathTime() {
        return death_time;
    }

    public int getrelev_coeff() {
        return relev_coeff;
    }

    public Mark(){}

    public Mark(double x, double y, String type, String description, String photo_path, Long deathTime, int relev_coeff) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.description = description;
        this.photo_path = photo_path;
        this.death_time = deathTime;
        this.relev_coeff = relev_coeff;
    }

}
