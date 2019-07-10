package com.example.repository;

import com.example.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User,Integer> {

    @Query("select u from User u where u.username = :name")
    User findByName(@Param("name") String name);

    @Query("select u from User u")
    Page<User> findAll(Pageable pageable);

    @Transactional
    @Modifying
    @Query("update User u set u.username = ?1 where u.id = ?2")
    int updateUserName(String name,Integer id);
}
