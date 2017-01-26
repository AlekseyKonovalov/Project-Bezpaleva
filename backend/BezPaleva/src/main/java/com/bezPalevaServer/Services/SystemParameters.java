package com.bezPalevaServer.Services;

import org.springframework.stereotype.Service;

@Service
public class SystemParameters {

    private int deathTimeSize = 5;
    private int irrelevanceLevelMax = 10;
    private int maxNumberMarksPerDay = 10;

    public void setDeathTimeSize(int deathTimeSize) {
        this.deathTimeSize = deathTimeSize;
    }

    public int getDeathTimeSize() {
        return deathTimeSize;
    }

    public void setIrrelevanceLevelMax(int irrelevanceLevelMax) {
        this.irrelevanceLevelMax = irrelevanceLevelMax;
    }

    public int getIrrelevanceLevelMax() {
        return irrelevanceLevelMax;
    }

    public int getMaxNumberMarksPerDay() {
        return maxNumberMarksPerDay;
    }

    public void setMaxNumberMarksPerDay(int maxNumberMarksPerDay) {
        this.maxNumberMarksPerDay = maxNumberMarksPerDay;
    }
}
