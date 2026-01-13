package com.example.demo.Controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Dati.UpdateUserRequest;
import com.example.demo.Dati.User;
import com.example.demo.Exception.UserAlreadyExistsException;
import com.example.demo.Exception.UserNotFoundException;
import com.example.demo.Security.UserDetailsImpl;
import com.example.demo.Service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<User> getMyProfile(
            @AuthenticationPrincipal UserDetailsImpl userDetails) throws UserNotFoundException {

        User user = userService.getUserById(
                userDetails.getUser().getId()
        );

        user.setPasswordHash(null); 
        return ResponseEntity.ok(user);
    }

    @PutMapping("/me")
    public ResponseEntity<User> updateMyProfile(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody UpdateUserRequest request) throws UserNotFoundException, UserAlreadyExistsException {

        User updatedData = new User();
        updatedData.setUsername(request.getUsername());
        updatedData.setEmail(request.getEmail());

        User updatedUser = userService.updateUser(
                userDetails.getUser().getId(),
                updatedData,
                request.getPassword()
        );

        updatedUser.setPasswordHash(null);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMyAccount(
            @AuthenticationPrincipal UserDetailsImpl userDetails) throws UserNotFoundException {

        userService.deleteUser(userDetails.getUser().getId());
        
        System.out.println("USER DETAILS = " + userDetails);
        return ResponseEntity.noContent().build();
    }
}
