package com.dehlan.Journal.service;

import com.dehlan.Journal.entity.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


/*
*
* Understand how to run Junit, how test are executed
*
* */
@SpringBootTest
public class UserServiceTests {
    /*
    @ParameterizedTest
    @CsvSource
    @Disabled
    @ArgumentsSource(UserArgumentsProvider.class)
    @BeforeEach
    @BeforeAll
    @AfterAll
    @AfterEach
    @CscSource
    @ArgumentsSource
    @Test
     */

    @Autowired
    UserService userService;
    @Test
    public void testFindByUserName(){

        User user = userService.findByUserName("Arjun Singh");
        System.out.println(user.getUserName());
    }
}
