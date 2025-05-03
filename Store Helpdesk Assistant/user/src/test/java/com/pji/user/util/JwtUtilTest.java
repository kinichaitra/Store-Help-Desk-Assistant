package com.pji.user.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
class JwtUtilTest {

    public static JwtUtil jwtUtil;

    @BeforeAll
    static void init() {
        jwtUtil=new JwtUtil();
    }

    @Test
    void generateToken() {

        String token = jwtUtil.generateToken("HariU");
        System.out.println("1"+token);
        assertNotNull(token);
    }

    @Test
    void validateToken() {

        UserDetails user = new User("HariU", "HariP", new ArrayList<>());
        String token = jwtUtil.generateToken("HariU");
        Boolean validateToken = jwtUtil.validateToken(token, user);
        System.out.println("2"+token+validateToken);
        assertEquals(true, validateToken);
    }
}