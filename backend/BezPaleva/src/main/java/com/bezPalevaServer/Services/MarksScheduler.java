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

        Timestamp currentTime =  new Timestamp(new Date().getTime());

        List<String> photosPaths = markRepository.getPhotosPaths(currentTime, systemParameters.getIrrelevanceLevelMax());

        for (String path : photosPaths) {
            new File(path).delete();
        }

        markRepository.deleteOutdatedMarks(currentTime, systemParameters.getIrrelevanceLevelMax());

    }
}
