package com.pji.user.service;

import com.pji.user.exception.UserNotFoundException;
import com.pji.user.model.MyUser;
import com.pji.user.model.UserCredentials;
import com.pji.user.repository.UserRepository;
import com.pji.user.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

@Service
public class UserService {
    private final JwtUtil jwtUtil;
    private final UserRepository repo;

    public UserService(JwtUtil jwtUtil, UserRepository repo) {
        this.jwtUtil = jwtUtil;
        this.repo = repo;
    }

    Logger logger = LoggerFactory.getLogger(UserService.class);
//    @Autowired
//    private JwtUtil jwtUtil;
//    @Autowired
//    private UserRepository repo;

    // Load user by username
//    public UserDetails loadUserByUsername(String userName) {
//        MyUser myUser = repo.findByUserName(userName);
//        return new User(myUser.getUserName(), myUser.getPassword(), new ArrayList<>());
    public UserDetails loadUserByUsername(String userName) {
        MyUser myUser = repo.findByUserName(userName);
        return new User(myUser.getUserName(), myUser.getPassword(), new ArrayList<>());

    }

    // Get user details and generate JWT token
    public UserCredentials getUserDetails(UserDetails user) {
        MyUser u = repo.findByUserName(user.getUsername());
        String token = jwtUtil.generateToken(user.getUsername());
        return new UserCredentials(token, u.getName(), u.getAccessLevel(), u.getUserName());
    }


    // Get access level of a user by username
    public String getAccessLevel(String username) {
        return repo.findByUserName(username).getAccessLevel();
    }


    // Get all users
    public List<MyUser> getAllUsers() {
        return repo.findAll();
    }

    // Get user by ID
    public Optional<MyUser> getUserById(Integer id) {

        return Optional.ofNullable(repo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found")));
    }

    // Add a new user
//    @Transactional

    public MyUser addUser(MyUser user) {
        try {
            LOGGER.info("user Added successfully-Service");
            return repo.save(user);
        } catch (Exception e) {
            LOGGER.error("Error Adding user-Service");
            throw new RuntimeException("Error adding user", e);
        }
    }

    public MyUser updateUser(int id, MyUser updatedUser) {
        logger.info("updateUser function being implemented-Service");
        Optional<MyUser> existingUser = repo.findById(id);
        if (!existingUser.isPresent()) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
            MyUser user = existingUser.get();
            user.setAccessLevel(updatedUser.getAccessLevel());
            user.setUserName(updatedUser.getUserName());
            user.setPassword(updatedUser.getPassword());
            return repo.save(user);
        }


    // Delete a user by ID
    public ResponseEntity<List> deleteUser(Integer id) {
        logger.info("deletestore function being implemented");
        if (!repo.existsById(id)) {
            throw new UserNotFoundException("User Not found");
        }
        repo.deleteById(id);
        return ResponseEntity.ok().build();
    }

}