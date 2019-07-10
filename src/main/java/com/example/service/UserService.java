package com.example.service;

import com.example.entity.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author zhengjiaxin
 * @date 2019/7/9
 */
@CacheConfig(cacheNames = "user",cacheManager = "cacheManager")
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Cacheable()
    public User getUserById(String name){
        User user = userRepository.findByName(name);
        return user;
    }
}
