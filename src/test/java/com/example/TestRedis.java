package com.example;

import com.example.entity.User;
import com.example.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhengjiaxin
 * @date 2019/7/8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedis {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;  //操作k-v都是字符串的

    @Autowired
    private RedisTemplate<Object,User> redisTemplate;

    @Test
    public void test1(){
        User user = userRepository.findByName("张三");
        redisTemplate.opsForValue().set("user-01",user);
    }
}
