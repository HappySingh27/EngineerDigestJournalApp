package com.dehlan.Journal.controller;

import com.dehlan.Journal.Scheduler.UserScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/schedule-email")
public class UserSchedulerController {

    @Autowired
    UserScheduler userScheduler;

    @PostMapping
    public void sendEmail(){
        userScheduler.performTask();

    }
}
