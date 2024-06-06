package com.example.ogani.repository;

import java.util.List;
import java.util.Optional;

import com.example.ogani.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.ogani.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    
    Optional<User> findByUsername(String username);


    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    @Query(value ="Select * from User where user_id = :id user by id desc",nativeQuery = true)
    List<User> getByIdUser(long id);
}
