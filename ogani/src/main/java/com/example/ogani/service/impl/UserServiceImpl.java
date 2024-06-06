package com.example.ogani.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ogani.entity.ERole;
import com.example.ogani.entity.Role;
import com.example.ogani.entity.User;
import com.example.ogani.exception.BadRequestException;
import com.example.ogani.exception.NotFoundException;
import com.example.ogani.model.request.ChangePasswordRequest;
import com.example.ogani.model.request.CreateUserRequest;
import com.example.ogani.model.request.UpdateProfileRequest;
import com.example.ogani.repository.RoleRepository;
import com.example.ogani.repository.UserRepository;
import com.example.ogani.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void register(CreateUserRequest request) {
        // TODO Auto-generated method stub
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setCountry(request.getCountry());
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        Set<String> strRoles = request.getRole();
          Set<Role> roles = new HashSet<>();
      
          if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
          } else {
            strRoles.forEach(role -> {
              switch (role) {
              case "admin":
                Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(adminRole);
      
                break;
              case "mod":
                Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(modRole);

                break;
              default:
                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
              }
            });
          }
          user.setRoles(roles);
          userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
      // TODO Auto-generated method stub
      User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Not Found User"));
      return user;
    }

    @Override
    public User updateUser(UpdateProfileRequest request) {
      // TODO Auto-generated method stub
      User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new NotFoundException("Not Found User"));
      user.setFirstname(request.getFirstname());
      user.setLastname(request.getLastname());
      user.setEmail(request.getEmail());
      user.setCountry(request.getCountry());
      user.setState(request.getState());
      user.setAddress(request.getAddress());
      user.setPhone(request.getPhone());
      userRepository.save(user);
      return user;
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
      // TODO Auto-generated method stub
       User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new NotFoundException("Not Found User"));

        String oldPassword = request.getOldPassword();
        String newPassword = request.getNewPassword();
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BadRequestException("Old Password Not Same");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

//       if(encoder.encode(request.getOldPassword()) != user.getPassword()){
//         throw new BadRequestException("Old Passrword Not Same");
//       }
//       user.setPassword(encoder.encode(request.getNewPassword()));
//
//       userRepository.save(user);
      
    }

    @Override
    public List<User>getList() {
        return userRepository.findAll(Sort.by("id").descending());
    }


}
