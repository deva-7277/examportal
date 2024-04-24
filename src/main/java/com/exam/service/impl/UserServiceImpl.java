//package com.exam.service.impl;
//
//import com.exam.helper.UserFoundException;
//import com.exam.model.User;
//import com.exam.model.UserRole;
//import com.exam.repo.RoleRepository;
//import com.exam.repo.UserRepository;
//import com.exam.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Set;
//
//
//@Service
//public class UserServiceImpl implements UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Override
//    public User createUser(User user, Set<UserRole> userRoles) throws Exception {
//        User local = this.userRepository.findByUsername(user.getUsername());
//        if(local!=null){
//            System.out.println("User is already present there !!");
//            throw new UserFoundException("User already present !!");
//        }
//        else{
////            user creation
//
////            Iterating roles got from set of userRoles and extracting roles from that and saving
////            into roleRepository
//            for (UserRole ur : userRoles){
//                this.roleRepository.save(ur.getRole());
//            }
//
////            getting set of roles of this users and adding all roles to that from request
//            user.getUserRoles().addAll(userRoles);
//            local = this.userRepository.save(user);
//        }
//        return local;
//    }
//
//
//    //Fetch user by username
//    @Override
//    public User getUser(String username) {
//        return this.userRepository.findByUsername(username);
//    }
//
//    //delete user by userId
//    @Override
//    public void deleteUser(Long userId) {
//        this.userRepository.deleteById(userId);
//    }
//}


package com.exam.service.impl;

import com.exam.helper.UserFoundException;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.repo.RoleRepository;
import com.exam.repo.UserRepository;
import com.exam.service.UserService;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
            throw new UserFoundException("User already present !!");
        }
        else{
//            user creation

//            Iterating roles got from set of userRoles and extracting roles from that and saving
//            into roleRepository
            for (UserRole ur : userRoles){
                this.roleRepository.save(ur.getRole());
            }

//            getting set of roles of this users and adding all roles to that from request
            user.getUserRoles().addAll(userRoles);
            local = this.userRepository.save(user);
        }
        return local;
    }


    //Fetch user by username
    @Override
    public User getUser(String username) {
        return this.userRepository.findByUsername(username);
    }

    //delete user by userId
    @Override
    public void deleteUser(Long userId) {
        this.userRepository.deleteById(userId);
}

    @Override
    public User updateUser(Long userId,User user) {
        User existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser != null) {
            // Update user properties
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPhone(user.getPhone());
            existingUser.setEnabled(user.isEnabled());

            // Handle image upload
            byte[] imageFile = user.getImage();
            if (imageFile != null) {
                existingUser.setImage(imageFile);
            }

            return userRepository.save(existingUser);
        }
        return null;
    }

}
