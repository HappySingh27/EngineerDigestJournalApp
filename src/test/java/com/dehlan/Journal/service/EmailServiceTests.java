package com.dehlan.Journal.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    EmailService service;

    @Test
    public void sendEmailTest(){
        service.sendEmails("sample@gmail.com","test subject","This is Body");
    }
}
