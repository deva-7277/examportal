package com.exam.service.impl;

import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.repo.RoleRepository;
import com.exam.repo.UserRepository;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User createUser(User user, Set<UserRole> userRoles) throws Exception {
        User local = this.userRepository.findByUsername(user.getUsername());
        if(local!=null){
            System.out.println("User is already present there !!");
            throw new Exception("User already present !!");
        }
        else{
//            user creation

//            Iterating roles got from set of userRoles and extracting roles from that and saving
//            into roleRepository
            for (UserRole ur : userRoles){
                roleRepository.save(ur.getRole());
            }

//            getting set of roles of this users and adding all roles to that from request
            user.getUserRoles().addAll(userRoles);
            local = userRepository.save(user);
        }
        return local;
    }
}
