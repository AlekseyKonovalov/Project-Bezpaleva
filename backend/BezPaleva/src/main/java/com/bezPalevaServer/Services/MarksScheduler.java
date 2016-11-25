package com.bezPalevaServer.Services;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


public class MarksScheduler {

    @Scheduled(fixedDelay = 5000)
    public void clearTempFolder() {
        System.out.println("I am a schedule-method with fixed delay param.");
        System.out.println("Executes after 1 minute when my previous execution ends.");
    }
}
