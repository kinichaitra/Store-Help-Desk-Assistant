package com.pji.user.controller;

import com.pji.user.exception.UserNotFoundException;
import com.pji.user.model.MyUser;
import com.pji.user.model.UserCredentials;
import com.pji.user.service.UserService;
import com.pji.user.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.POST,RequestMethod.DELETE,RequestMethod.GET,RequestMethod.PUT,RequestMethod.PATCH})
@RestController
@Slf4j
@RequestMapping("/authorization")
public class AuthorizationController {
    private final JwtUtil jwtUtil;
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(AuthorizationController.class);
    public AuthorizationController(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

//@Value("${userDetails.badCredentialsMessage}")
//private String BAD_CREDENTIALS_MESSAGE;

    @GetMapping("/login")
    public ResponseEntity login(@RequestHeader(name="userName") String userName,
                                @RequestHeader(name="password") String password) {
        //Varify credentials
        if (userName == null || password == null || userName.trim().isEmpty() || password.trim().isEmpty()) {
            throw new UserNotFoundException("Username or password cannot be Null");
        }
        else {
            try {
                UserDetails user = userService.loadUserByUsername(userName);

                if (user.getPassword().equals(password)) {
                    UserCredentials userDetails = userService.getUserDetails(user);
                    log.info("Login Successful");
                    return new ResponseEntity(userDetails, HttpStatus.OK);

                } else {
                    log.info("Login unsuccessful --> Invalid password");
                    throw new UserNotFoundException("Password is wrong");
                }

            } catch (Exception e) {
                log.error("Login unsuccessful --> Invalid Credentials");
                throw new UserNotFoundException("Invalid Credentials");
            }
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validate(@RequestHeader(name = "Authorization") String token1) {
        System.out.println(token1);
        //returns response after validating received token
        String token = token1.substring(7);
        try {
            log.info("Validating token...");
            System.out.println("Validating....");
            UserDetails user = userService.loadUserByUsername(jwtUtil.extractUsername(token));
            if (jwtUtil.validateToken(token, user)) {
                log.info("Token is valid");
                return new ResponseEntity<>(true, HttpStatus.OK);
            } else {
                log.error("Token is invalid");
                return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.error("Token validation failed", e);
            return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
        }
    }
    @GetMapping("/isHelpdesk")
    public ResponseEntity<Boolean> isHelpdesk(@RequestHeader(name = "Authorization") String token1){
        String token = token1.substring(7);
        try{
            String username=jwtUtil.extractUsername(token);
            String accessLevel=userService.getAccessLevel(username);
            if(accessLevel.equals("HD"))
                return new ResponseEntity<>(true, HttpStatus.OK);
            else
                return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            log.error("Error checking helpdesk access level", e);
            return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
        }
    }
    @GetMapping("/isAdmin")
    public ResponseEntity<Boolean> isAdmin(@RequestHeader(name = "Authorization") String token1) {
        String token = token1.substring(7);
        try {
            String username = jwtUtil.extractUsername(token);
            String accessLevel = userService.getAccessLevel(username);
            if (accessLevel.equals("AD"))
                return new ResponseEntity<>(true, HttpStatus.OK);
            else
                return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            log.error("Error checking admin access level", e);
            return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
        }
    }




    //        @PostMapping("/signup")
//        public ResponseEntity<?> signup(@RequestBody User user) {
//            try {
//                User newUser = userService.signup(user);
//                return new ResponseEntity<>(newUser, HttpStatus.CREATED);
//            } catch (Exception e) {
//                return new ResponseEntity<>("Error creating user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        }

    @GetMapping
    public ResponseEntity<List<MyUser>> getAllUsers() {
        List<MyUser> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<MyUser> getUserById(@PathVariable Integer id) {
        MyUser user = userService.getUserById(id).orElseThrow(() -> new UserNotFoundException("User not found of id "));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    //    @Transactional
    @PostMapping("/user")
    public ResponseEntity<MyUser> addUser(@RequestBody MyUser user) {
        LOGGER.info("user Added successfully-Controller");
        MyUser newUser = userService.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<MyUser> updateUser(@PathVariable Integer id,
                                             @RequestBody MyUser updatedUser) {
        LOGGER.info("user updated successfully-Controller");
        MyUser updated = userService.updateUser(id, updatedUser);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

