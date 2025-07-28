package com.dehlan.Journal.repository;

import com.dehlan.Journal.entity.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserRepositoryCreteriaTests {

    @Autowired
    UserRepositoryCreteria userRepositoryCreteria;

    @Test
    @Disabled
    public void testCreteriaQuery(){

        List<User> userForSA = userRepositoryCreteria.getUserForSA();

        System.out.println(userForSA);

    }
}
