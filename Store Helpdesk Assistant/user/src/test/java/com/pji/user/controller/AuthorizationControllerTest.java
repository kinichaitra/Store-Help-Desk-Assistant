//package com.pji.user.controller;
//
//import com.pji.user.exception.UserNotFoundException;
//import com.pji.user.model.MyUser;
//import com.pji.user.repository.UserRepository;
//import com.pji.user.service.UserService;
//import com.pji.user.util.JwtUtil;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockedStatic;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static jdk.internal.org.jline.utils.InfoCmp.Capability.user1;
//import static jdk.internal.org.jline.utils.InfoCmp.Capability.user2;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//class AuthorizationControllerTest {
//
//
//    @InjectMocks
//    AuthorizationController authController;
//
//    @Mock
//    JwtUtil jwtUtil;
//
//    @Mock
//    UserService userService;
//
//    @Mock
//    UserRepository repo;
//
//    @Test
//    public void validLoginTest() {
//
//        MyUser user =new MyUser(1,"John","MN","John","John@123");
//        UserDetails value = new User(user.getUserName(),user.getPassword(),new ArrayList<>());
//        when(userService.loadUserByUsername("John")).thenReturn(value);
//        when(jwtUtil.generateToken(user.getUserName())).thenReturn("token");
//        ResponseEntity<?> login = authController.login(user.getUserName(), user.getPassword());
//        assertEquals(200,login.getStatusCodeValue());
//    }
//    @Test
//    public void userNameNotFoundLoginTest(){
//        MyUser user =new MyUser(1,"John","MN","John","John@13");
//        Exception exception = assertThrows(UserNotFoundException.class, ()-> {
//            authController.login(user.getUserName(), user.getPassword());
//        });
//        String expectedMessage = "Invalid Credentials";
//        String actualMessage = exception.getMessage();
//
//        assertEquals(actualMessage,expectedMessage);
//    }
//
//    @Test
//    public void validateTest(){
//        UserDetails value = new User("John","John@123",new ArrayList<>());
//        when(jwtUtil.extractUsername("token")).thenReturn("John");
//        when(userService.loadUserByUsername("John")).thenReturn(value);
//        when(jwtUtil.validateToken("token",value)).thenReturn(true);
//        assertTrue(authController.validate("Bearer token").getBody());
//        assertEquals(200,authController.validate("Bearer token").getStatusCodeValue());
//    }
//
//    @Test
//    public void validateTest2() {
//        UserDetails value = new User("John", "John@123", new ArrayList<>());
//        when(jwtUtil.extractUsername("token")).thenReturn("John");
//        when(userService.loadUserByUsername("John")).thenReturn(value);
//
//        when(jwtUtil.validateToken("token", value)).thenReturn(false);
//        assertFalse(authController.validate("Bearer token").getBody());
//        assertEquals(403, authController.validate("Bearer token").getStatusCodeValue());
//    }
//
//    @Test
//    public void isHelpdeskTest(){
//        UserDetails value = new User("John","John@123",new ArrayList<>());
//        when(jwtUtil.extractUsername("token")).thenReturn("JohnU");
//        when(userService.getAccessLevel("JohnU")).thenReturn("HD");
//        assertTrue(authController.isHelpdesk("Bearer token").getBody());
//        assertEquals(200,authController.isHelpdesk("Bearer token").getStatusCodeValue());
//    }
//
//    @Test
//    public void isHelpdeskTest2(){
//        UserDetails value = new User("John","John@123",new ArrayList<>());
//        when(jwtUtil.extractUsername("token")).thenReturn("John");
//        when(userService.getAccessLevel("John")).thenReturn("MN");
//        assertFalse(authController.isHelpdesk("Bearer token").getBody());
//        assertEquals(403,authController.isHelpdesk("Bearer token").getStatusCodeValue());
//    }
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        user1 = new MyUser(1, "John Doe");
//        user2 = new MyUser(2, "Jane Doe");
//    }
//
//    @Test
//    public void testGetAllUsers() {
//        List<MyUser> users = Arrays.asList(user1, user2);
//        when(userService.getAllUsers()).thenReturn(users);
//
//        ResponseEntity<List<MyUser>> response = authController.getAllUsers();
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(users, response.getBody());
//    }
//
//    @Test
//    public void testGetUserById() {
//        when(userService.getUserById(1)).thenReturn(Optional.of(user1));
//
//        ResponseEntity<MyUser> response = authController.getUserById(1);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(user1, response.getBody());
//    }
//
//    @Test
//    public void testAddUser() {
//        when(userService.addUser(user1)).thenReturn(user1);
//
//        ResponseEntity<MyUser> response = authController.addUser(user1);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals(user1, response.getBody());
//    }
//
//    @Test
//    public void testUpdateUser() {
//        when(userService.updateUser(1, user1)).thenReturn(user1);
//
//        ResponseEntity<MyUser> response = authController.updateUser(1, user1);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(user1, response.getBody());
//    }
//
//    @Test
//    public void testDeleteUser() {
//        doNothing().when(userService).deleteUser(1);
//
//        ResponseEntity<Void> response = authController.deleteUser(1);
//
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//    }
//
//
//}
package com.pji.user.controller;

import com.pji.user.exception.UserNotFoundException;
import com.pji.user.model.MyUser;
import com.pji.user.model.UserCredentials;
import com.pji.user.service.UserService;
import com.pji.user.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthorizationControllerTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthorizationController authorizationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogin_Success() {
        String userName = "testUser";
        String password = "testPass";
        UserDetails userDetails = mock(UserDetails.class);
        UserCredentials userCredentials = new UserCredentials();

        when(userService.loadUserByUsername(userName)).thenReturn(userDetails);
        when(userDetails.getPassword()).thenReturn(password);
        when(userService.getUserDetails(userDetails)).thenReturn(userCredentials);

        ResponseEntity response = authorizationController.login(userName, password);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userCredentials, response.getBody());
    }

    @Test
    public void testLogin_InvalidPassword() {
        String userName = "testUser";
        String password = "testPass";
        UserDetails userDetails = mock(UserDetails.class);

        when(userService.loadUserByUsername(userName)).thenReturn(userDetails);
        when(userDetails.getPassword()).thenReturn("wrongPass");

        assertThrows(UserNotFoundException.class, () -> {
            authorizationController.login(userName, password);
        });
    }

    @Test
    public void testValidate_ValidToken() {
        String token = "Bearer testToken";
        String username = "testUser";
        UserDetails userDetails = mock(UserDetails.class);

        when(jwtUtil.extractUsername(token.substring(7))).thenReturn(username);
        when(userService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtUtil.validateToken(token.substring(7), userDetails)).thenReturn(true);

        ResponseEntity<Boolean> response = authorizationController.validate(token);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
    }

    @Test
    public void testValidate_InvalidToken() {
        String token = "Bearer testToken";
        String username = "testUser";
        UserDetails userDetails = mock(UserDetails.class);

        when(jwtUtil.extractUsername(token.substring(7))).thenReturn(username);
        when(userService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtUtil.validateToken(token.substring(7), userDetails)).thenReturn(false);

        ResponseEntity<Boolean> response = authorizationController.validate(token);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertFalse(response.getBody());
    }

    @Test
    public void testGetAllUsers() {
        List<MyUser> users = Arrays.asList(new MyUser(), new MyUser());

        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<MyUser>> response = authorizationController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    public void testGetUserById_UserExists() {
        int userId = 1;
        MyUser user = new MyUser();

        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<MyUser> response = authorizationController.getUserById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testGetUserById_UserNotFound() {
        int userId = 1;

        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            authorizationController.getUserById(userId);
        });
    }

    @Test
    public void testAddUser() {
        MyUser user = new MyUser();
        MyUser newUser = new MyUser();

        when(userService.addUser(user)).thenReturn(newUser);

        ResponseEntity<MyUser> response = authorizationController.addUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(newUser, response.getBody());
    }

    @Test
    public void testUpdateUser() {
        int userId = 1;
        MyUser updatedUser = new MyUser();

        when(userService.updateUser(userId, updatedUser)).thenReturn(updatedUser);

        ResponseEntity<MyUser> response = authorizationController.updateUser(userId, updatedUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUser, response.getBody());
    }

    @Test
    public void testDeleteUser() {
        int userId = 1;

        doNothing().when(userService).deleteUser(userId);

        ResponseEntity<Void> response = authorizationController.deleteUser(userId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
