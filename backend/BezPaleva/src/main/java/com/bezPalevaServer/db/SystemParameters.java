package com.bezPalevaServer.db;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SystemParameters {

    @Id
    private Integer id;
    private int deathTimeSize;
    private int irrelevanceLevelMax;
    private int maxNumberMarksPerDay;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getDeathTimeSize() {
        return deathTimeSize;
    }

    public void setDeathTimeSize(int deathTimeSize) {
        this.deathTimeSize = deathTimeSize;
    }

    public int getIrrelevanceLevelMax() {
        return irrelevanceLevelMax;
    }

    public void setIrrelevanceLevelMax(int irrelevanceLevelMax) {
        this.irrelevanceLevelMax = irrelevanceLevelMax;
    }

    public int getMaxNumberMarksPerDay() {
        return maxNumberMarksPerDay;
    }

    public void setMaxNumberMarksPerDay(int maxNumberMarksPerDay) {
        this.maxNumberMarksPerDay = maxNumberMarksPerDay;
    }

    public SystemParameters() {}
}

