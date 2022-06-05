package com.krishnendu.projectshareplussvc.controller;

import com.krishnendu.projectshareplussvc.model.User;
import com.krishnendu.projectshareplussvc.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
public class UserController {

    @Autowired
    private IUserService _userService;

    @Autowired
    private PasswordEncoder _passwordEncoder;

    /**
     * endpoint for creation of new user
     * @param user user model
     * @return user
     * @throws Exception any-type of jpa exception
     */
    @PostMapping("/new")
    public User createNewUser(@RequestBody User user) throws Exception {
        user.setPassword(getEncodedPassword(user.getPassword()));
        return _userService.registerUser(user);
    }

    /**
     * method encode password before saving to db
     * @param password as string
     * @return encoded password
     */
    private String getEncodedPassword(String password) {
        return _passwordEncoder.encode(password);
    }
}
