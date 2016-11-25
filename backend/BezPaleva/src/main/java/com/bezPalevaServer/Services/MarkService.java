package com.bezPalevaServer.Services;

import com.bezPalevaServer.db.Mark;
import com.bezPalevaServer.db.MarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class MarkService {

    @Autowired
    MarkRepository repository;

    private int deathTimeSize = 5;
    private int irrelevanceLevelMax = 10;

    public ArrayList<Mark> getMarksByRadius(double x, double y, int radius){

        ArrayList<Mark> marks = new ArrayList<Mark>();

        double xPow, yPow;

        for (Mark mark : repository.getAllMarksFromDB(x,y,radius)) {
            xPow = Math.pow(mark.getX(),2);
            yPow = Math.pow(mark.getY(),2);
            if(Math.sqrt(xPow+yPow) <= radius)
                marks.add(mark);
        }

        return marks;
    }

    @Transactional
    public Mark addMarkInDB(Mark mark){
        return repository.save(mark);
    }

    @Transactional
    public Mark getMarkFromDB(long id){
        return repository.findOne(id);
    }


    public void setDeathTimeSize(int deathTimeSize) {
        this.deathTimeSize = deathTimeSize;
    }

    public int getDeathTimeSize() {
        return deathTimeSize;
    }

    public void setirrelevanceLevelMax(int irrelevanceLevelMax) {
        this.irrelevanceLevelMax = irrelevanceLevelMax;
    }

    public int getirrelevanceLevelMax() {

        return irrelevanceLevelMax;
    }

}
