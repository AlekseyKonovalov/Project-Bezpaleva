package com.bezPalevaServer.Services;

import com.bezPalevaServer.db.Mark;
import com.bezPalevaServer.db.MarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@EnableScheduling
@Component
@Service
public class MarksScheduler {

    @Autowired
    MarkRepository markRepository;
    @Autowired
    SystemParameters systemParameters;

    @Transactional
    @Scheduled(fixedDelay = 3600000)
    public void deleteMarks() {

        List<Mark> marks = markRepository.getMarksForDeletion(new Timestamp(new Date().getTime()), systemParameters.getIrrelevanceLevelMax());

        for (Mark mark: marks) {
            String photoPath = mark.getphoto_path();
            if(photoPath!= null) new File(photoPath).delete();
            markRepository.delete(mark);
        }

    }
}
