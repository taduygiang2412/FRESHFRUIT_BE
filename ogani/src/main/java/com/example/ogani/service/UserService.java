package com.example.ogani.service;

import com.example.ogani.entity.Order;
import com.example.ogani.entity.User;
import com.example.ogani.model.request.ChangePasswordRequest;
import com.example.ogani.model.request.CreateUserRequest;
import com.example.ogani.model.request.UpdateProfileRequest;

import java.util.List;

public interface UserService {
    
    void register(CreateUserRequest request);


    User getUserByUsername(String username);

    User updateUser(UpdateProfileRequest request);

    void changePassword(ChangePasswordRequest request);

    List<User> getList();

}
