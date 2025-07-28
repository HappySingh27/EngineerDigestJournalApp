package com.dehlan.Journal.Scheduler;

import com.dehlan.Journal.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UserScheduler {

    @Autowired
    EmailService emailService;

    @Scheduled(cron="0 * * * * *")
    public void performTask(){

            emailService.sendEmails("yjsapeoj@sharklasers.com","Hi","Hi");
    }

}
