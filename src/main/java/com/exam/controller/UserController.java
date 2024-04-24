package com.exam.controller;

import com.exam.helper.UserFoundException;
import com.exam.model.ImageModel;
import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.service.UserService;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
@EnableWebMvc
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/")
    public User createUser(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("firstName") String firstName,
                           @RequestParam("lastName") String lastName,
                           @RequestParam("email") String email,
                           @RequestParam("phone") String phone,
                           @RequestParam("imageFile") MultipartFile imageFile) throws Exception {
        // Create a new User object and populate its fields with the provided request parameters
        User user = new User();
        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhone(phone);

        // Process the imageFile and save it to the database
        ImageModel imageModel = saveImage(imageFile);
        user.setImage(imageModel.getImageData());

        // Create the user role and set it for the user
        Role role = new Role();
        role.setRoleId(45L);
        role.setRoleName("NORMAL");

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);

        Set<UserRole> roles = new HashSet<>();
        roles.add(userRole);
        user.setUserRoles(roles);

        // Call the service to create the user
        return userService.createUser(user,roles);
    }

    private ImageModel saveImage(MultipartFile imageFile) throws Exception {
        // Process the image file and save it to the database
        if (!imageFile.isEmpty()) {
            // Extract the necessary information from the image file and create the ImageModel object
            String imageName = imageFile.getOriginalFilename();
            String contentType = imageFile.getContentType();
            byte[] imageBytes = imageFile.getBytes();

            ImageModel imageModel = new ImageModel(imageName, contentType, imageBytes);

            // Save the image to the database using your image repository or service
            // Example: imageRepository.save(imageModel);

            return imageModel;
        }

        return null;
    }

    // Fetch user
    @GetMapping("/{username}")
    public User getUser(@PathVariable("username") String username) {
        return userService.getUser(username);
    }

    // Delete user
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return "User with user id " + userId + " deleted Successfully.";
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long userId,
            @RequestBody User user
    ) {
        User updatedUser = userService.updateUser(userId, user);
        return ResponseEntity.ok(updatedUser);
    }

    @ExceptionHandler(UserFoundException.class)
    public ResponseEntity<?> exceptionHandler(UserFoundException ex) {
        return ResponseEntity.ok(ex.getMessage());
    }
}
