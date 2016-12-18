package com.bezPalevaServer.Services;

import com.bezPalevaServer.db.Mark;
import com.bezPalevaServer.db.MarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class MarkService {

    @Autowired
    MarkRepository repository;

    final double KILOMETER =  0.016151166114884101907362740362;

    public ArrayList<Mark> getMarksByRadius(double x, double y, int radius){

        ArrayList<Mark> marks = new ArrayList<Mark>();

        double radX = Math.toRadians(x);
        double radY = Math.toRadians(y);

        double radiusOnMap = KILOMETER * radius;

        for (Mark mark : repository.getAllMarksFromDB(x,y,radiusOnMap)) {

            double dist =  6378137 * Math.acos(Math.cos(radX) * Math.cos(Math.toRadians(mark.getX())) * Math.cos( radY - Math.toRadians(mark.getY()) ) + Math.sin(radX) * Math.sin( Math.toRadians(mark.getX())));
            if(dist < radius * 1000)
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

    public void createPhotoFile(MultipartFile photoFile, Mark mark, String photoPath) throws IOException {

        String typePhoto = photoFile.getContentType();

        if(typePhoto.contains("image")){

            File file;
            if (photoPath != null) file = new File(photoPath);
            else file = new File("C:/photos/" + photoFile.hashCode() + photoFile.getOriginalFilename());

            photoFile.transferTo(file);

            mark.setPhotoPath(file.getAbsolutePath());
        }
    }
}
