package com.example.controller;

import com.example.entity.User;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhengjiaxin
 * @date 2019/7/5
 */
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/user/getAll")
    public Page<User> getAllUser(){
        return userRepository.findAll(new PageRequest(1,2));
    }

    @GetMapping("/user/{name}")
    public User getUserByName(@PathVariable("name") String name){
        return userService.getUserById(name);
    }


}
