package com.example.usersapi.service;

import com.example.usersapi.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    UserRoleRepository userRoleRepository;

    @Override
    public UserRole createRole(UserRole newRole){
        return userRoleRepository.save(newRole);
    }
}
