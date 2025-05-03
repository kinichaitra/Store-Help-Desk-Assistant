//package com.pji.user.service;
//
//import com.pji.user.model.MyUser;
//import com.pji.user.model.UserCredentials;
//import com.pji.user.repository.UserRepository;
//import com.pji.user.util.JwtUtil;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.core.userdetails.User;
//
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//@RunWith(MockitoJUnitRunner.class)
//class UserServiceTest {
//
//    @Mock
//    JwtUtil jwtUtil;
//
//    @Mock
//    UserRepository repo;
//
//    @InjectMocks
//    UserService service;
//
//    static MyUser myUser;
//    static User u;
//    static UserCredentials uc;
//
//    @BeforeAll
//    static void init() {
//        myUser=new MyUser(1,"John","Manager","John","John@123");
//        u=new User("JohnU", "John@123", new ArrayList<>());
//        uc=new UserCredentials("token","John","Manager","John");
//    }
//
//    @Test
//    void loadUserByUsername() {
//        when(repo.findByUserName("John")).thenReturn(myUser);
//        assertEquals(u,service.loadUserByUsername("John"));
//    }
//
//    @Test
//    void getUserDetails() {
//        when(repo.findByUserName("John")).thenReturn(myUser);
//        when(jwtUtil.generateToken("John")).thenReturn("token");
//        assertEquals(uc,service.getUserDetails(u));
//    }
//}

package com.pji.user.service;

import com.pji.user.exception.UserNotFoundException;
import com.pji.user.model.MyUser;
import com.pji.user.model.UserCredentials;
import com.pji.user.repository.UserRepository;
import com.pji.user.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserRepository repo;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUserByUsername_UserExists() {
        String userName = "testUser";
        MyUser myUser = new MyUser();
        myUser.setUserName(userName);
        myUser.setPassword("testPass");

        when(repo.findByUserName(userName)).thenReturn(myUser);

        UserDetails userDetails = userService.loadUserByUsername(userName);

        assertNotNull(userDetails);
        assertEquals(userName, userDetails.getUsername());
        assertEquals("testPass", userDetails.getPassword());
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        String userName = "testUser";

        when(repo.findByUserName(userName)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> {
            userService.loadUserByUsername(userName);
        });
    }

    @Test
    public void testGetUserDetails() {
        String userName = "testUser";
        MyUser myUser = new MyUser();
        myUser.setUserName(userName);
        myUser.setName("Test User");
        myUser.setAccessLevel("USER");

        UserDetails userDetails = new User(userName, "testPass", new ArrayList<>());
        when(repo.findByUserName(userName)).thenReturn(myUser);
        when(jwtUtil.generateToken(userName)).thenReturn("testToken");

        UserCredentials userCredentials = userService.getUserDetails(userDetails);

        assertNotNull(userCredentials);
        assertEquals("testToken", userCredentials.getToken());
        assertEquals("Test User", userCredentials.getName());
        assertEquals("USER", userCredentials.getAccessLevel());
        assertEquals(userName, userCredentials.getUserName());
    }

    @Test
    public void testGetAccessLevel() {
        String userName = "testUser";
        MyUser myUser = new MyUser();
        myUser.setUserName(userName);
        myUser.setAccessLevel("ADMIN");

        when(repo.findByUserName(userName)).thenReturn(myUser);

        String accessLevel = userService.getAccessLevel(userName);

        assertEquals("ADMIN", accessLevel);
    }

    @Test
    public void testGetAllUsers() {
        List<MyUser> users = new ArrayList<>();
        users.add(new MyUser());
        users.add(new MyUser());

        when(repo.findAll()).thenReturn(users);

        List<MyUser> result = userService.getAllUsers();

        assertEquals(2, result.size());
    }

    @Test
    public void testGetUserById_UserExists() {
        int userId = 1;
        MyUser myUser = new MyUser();

        when(repo.findById(userId)).thenReturn(Optional.of(myUser));

        Optional<MyUser> result = userService.getUserById(userId);

        assertTrue(result.isPresent());
        assertEquals(myUser, result.get());
    }

    @Test
    public void testGetUserById_UserNotFound() {
        int userId = 1;

        when(repo.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById(userId);
        });
    }

    @Test
    public void testAddUser() {
        MyUser user = new MyUser();
        MyUser savedUser = new MyUser();

        when(repo.save(user)).thenReturn(savedUser);

        MyUser result = userService.addUser(user);

        assertEquals(savedUser, result);
    }

    @Test
    public void testUpdateUser_UserExists() {
        int userId = 1;
        MyUser existingUser = new MyUser();
        MyUser updatedUser = new MyUser();
        updatedUser.setUserName("updatedUser");
        updatedUser.setPassword("updatedPass");
        updatedUser.setAccessLevel("ADMIN");

        when(repo.findById(userId)).thenReturn(Optional.of(existingUser));
        when(repo.save(existingUser)).thenReturn(existingUser);

        MyUser result = userService.updateUser(userId, updatedUser);

        assertEquals("updatedUser", result.getUserName());
        assertEquals("updatedPass", result.getPassword());
        assertEquals("ADMIN", result.getAccessLevel());
    }

    @Test
    public void testUpdateUser_UserNotFound() {
        int userId = 1;
        MyUser updatedUser = new MyUser();

        when(repo.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.updateUser(userId, updatedUser);
        });
    }

    @Test
    public void testDeleteUser_UserExists() {
        int userId = 1;

        when(repo.existsById(userId)).thenReturn(true);
        doNothing().when(repo).deleteById(userId);

        ResponseEntity<List> response = userService.deleteUser(userId);

        assertEquals(ResponseEntity.ok().build(), response);
    }

    @Test
    public void testDeleteUser_UserNotFound() {
        int userId = 1;

        when(repo.existsById(userId)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> {
            userService.deleteUser(userId);
        });
    }
}
