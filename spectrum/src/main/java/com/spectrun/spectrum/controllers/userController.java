package com.spectrun.spectrum.controllers;

import com.spectrun.spectrum.DTO.UserDTO;
import com.spectrun.spectrum.services.Implementations.JWTService;
import com.spectrun.spectrum.services.Implementations.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path ="/api/v1/users")
public class userController {
    private static final Logger log = LoggerFactory.getLogger(userController.class);
    UserService userService;
    java.util.logging.Logger logger = java.util.logging.Logger.getLogger(JWTService.class.getName());

    @Autowired
    public userController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/get/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("this is the logged in user"+ authentication.getName());
        List<UserDTO> usersList = this.userService.getUsers();
        return new ResponseEntity<>(usersList,HttpStatus.OK);
    }
    @PutMapping("/user/new/subscription/{id}/{subscription}")
    public ResponseEntity<?>addUserSuscription(@PathVariable("id") long userId,@PathVariable("subscription") long subscriptionId ){
        UserDTO updatedUser = this.userService.addSubscriptionToUserAccount(userId,subscriptionId);
        if(updatedUser != null){
            return new ResponseEntity<>(updatedUser,HttpStatus.OK);
        }

        return new ResponseEntity<>("Operation Failed",HttpStatus.OK);
    }
    //@PutMapping("/user/upgrade/subscription")



}
