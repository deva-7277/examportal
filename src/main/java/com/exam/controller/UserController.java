//package com.exam.controller;
//
//import com.exam.helper.UserFoundException;
//import com.exam.model.ImageModel;
//import com.exam.model.Role;
//import com.exam.model.User;
//import com.exam.model.UserRole;
//import com.exam.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartResolver;
//
//import javax.servlet.MultipartConfigElement;
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.util.HashSet;
//import java.util.Set;
//
//
//
//@RestController
//@RequestMapping("/user")
//@CrossOrigin("*")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//
//
//
//    @PostMapping(value = {"/"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public User createUser(@RequestPart("user") User user , @RequestPart("imageFile") MultipartFile[] file) throws Exception {
//
//
//
////        Encrypting password with Bcrypt password encoder
//        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
//
//
//
//
//        Role role = new Role();
//        role.setRoleId(45L);
//        role.setRoleName("NORMAL");
//
//        Set<UserRole> roles = new HashSet<>();
//        UserRole userRole = new UserRole();
//        userRole.setUser(user);
//        userRole.setRole(role);
//        user.setProfile("default.png");
//        roles.add(userRole);
//
//        try{
//            Set<ImageModel> images = uploadImage(file);
//            user.setUserImages(images);
//            return userService.createUser(user,roles);
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//            return  null;
//        }
//    }
//
//    public Set<ImageModel> uploadImage(MultipartFile[] multipartFiles) throws IOException {
//        Set<ImageModel> imageModels = new HashSet<>();
//        for(MultipartFile file : multipartFiles){
//            ImageModel imageModel = new ImageModel(
//                    file.getOriginalFilename(),
//                    file.getContentType(),
//                    file.getBytes()
//            );
//            imageModels.add(imageModel);
//        }
//        return imageModels;
//    }
//
//    //fetch user
//    @GetMapping("/{username}")
//    public User getUser(@PathVariable("username") String username){
//        return this.userService.getUser(username);
//    }
//
//    //delete user
//    @DeleteMapping("/{userId}")
//    public String deleteUser(@PathVariable("userId") Long userId){
//        this.userService.deleteUser(userId);
//        return "User with user id " + userId + " deleted Successfully.";
//    }
//
//
//    @ExceptionHandler(UserFoundException.class)
//    public ResponseEntity<?> exceptionHandler(UserFoundException ex) {
//        return ResponseEntity.ok(ex.getMessage());
//    }
//}

package com.exam.controller;

import com.exam.helper.UserFoundException;
import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/")
    public User createUser(@RequestBody User user) throws Exception {

        user.setProfile("default.png");

//        Encrypting password with Bcrypt password encoder
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));

        Role role = new Role();
        role.setRoleId(45L);
        role.setRoleName("NORMAL");

        Set<UserRole> roles = new HashSet<>();
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);

        roles.add(userRole);

        return this.userService.createUser(user,roles);
    }

    //fetch user
    @GetMapping("/{username}")
    public User getUser(@PathVariable("username") String username){
        return this.userService.getUser(username);
    }

    //delete user
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable("userId") Long userId){
        this.userService.deleteUser(userId);
        return "User with user id " + userId + " deleted Successfully.";
    }


    @ExceptionHandler(UserFoundException.class)
    public ResponseEntity<?> exceptionHandler(UserFoundException ex) {
        return ResponseEntity.ok(ex.getMessage());
}
}