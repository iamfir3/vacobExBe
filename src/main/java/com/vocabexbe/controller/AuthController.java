package com.vocabexbe.controller;

import com.vocabexbe.ApiException;
import com.vocabexbe.dto.AuthDTO;
import com.vocabexbe.entity.User;
import com.vocabexbe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping()
    @CrossOrigin
    private ResponseEntity<?> login(@RequestBody AuthDTO authDTO){
        Optional<User> user= userRepository.findByUsername(authDTO.getUsername());
        if (user.isEmpty()){
            throw new ApiException(HttpStatus.BAD_REQUEST, "Incorrect username/password");
        }

        if(!user.get().getPassword().equals(authDTO.getPassword())){
            throw new ApiException(HttpStatus.BAD_REQUEST, "Incorrect username/password");
        }
        return ResponseEntity.ok("Success");
    }
}
