package com.bezPalevaServer.Services;


import com.bezPalevaServer.db.MarkRepository;
import com.bezPalevaServer.db.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
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
public class Scheduler {

    @Autowired
    MarkRepository markRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SystemParameters systemParameters;

    @Transactional
    @Scheduled(fixedDelay = 3600000)
    public void deleteMarks() {

        Timestamp currentTime =  new Timestamp(new Date().getTime());

        List<String> photosPaths = markRepository.getPhotosPaths(currentTime, systemParameters.getIrrelevanceLevelMax());

        for (String path : photosPaths) {
            new File(path).delete();
        }

        markRepository.deleteOutdatedMarks(currentTime, systemParameters.getIrrelevanceLevelMax());

    }

    @Scheduled(fixedDelay = 86400000)
    public void resetNumberMarksPerDay(){

        userRepository.resetNumberMarks();
    }
}
