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

    public ArrayList<Mark> getMarksByRadius(double x, double y, int radius){

        ArrayList<Mark> marks = new ArrayList<Mark>();

        for (Mark mark : repository.findAll()) {
                if(mark.getX() <= x+radius && mark.getX() >= x-radius && mark.getY() <= radius+y && mark.getY() >= y-radius){
                    marks.add(mark);
                }
        }
        return marks;
    }

    @Transactional
    public Mark addMarkInDB(Mark mark){
        return repository.save(mark);
    }
}
