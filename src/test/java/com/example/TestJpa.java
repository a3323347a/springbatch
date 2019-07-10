package com.example;

import com.example.entity.User;
import com.example.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestJpa {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test1(){

        Iterable<User> list = userRepository.findAll();
        for (User user:list){
            System.out.println(user);
        }
    }

    @Test
    public void test2(){
        Optional<User> user = userRepository.findById(1);
        System.out.println(user);
    }

    @Test
    public void test3(){
        User user = userRepository.findByName("王五1");
        System.out.println(user);
    }

    @Test
    public void test4(){
        int result = userRepository.updateUserName("张三",1);
        System.out.println("结果影响：" + result + "行");
    }
}
