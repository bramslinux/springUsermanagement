package com.example.userManagement.controller;

import com.example.userManagement.entity.User;
import com.example.userManagement.repository.UserRepository;
import com.example.userManagement.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.userManagement.dto.UserDTO;

@RestController
@RequestMapping("/auth/")
public class AuthController {
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    private  final JwtService jwtService;
    public AuthController(UserRepository repo,PasswordEncoder passwordEncoder,JwtService jwtService){
        this.repo = repo;
        this.passwordEncoder=passwordEncoder;
        this.jwtService=jwtService;
    }
    @PostMapping("/login")
    public String login(@RequestBody UserDTO loginData){
        User user = repo.findByUsername(loginData.getUsername()).orElseThrow(()-> new RuntimeException("ulisateur non trouver"));
        if(passwordEncoder.matches(loginData.getPassword(),user.getPassword())){
            return jwtService.generateToken(user.getUsername());
        }else {
            throw new RuntimeException("Mauvais mot de passe !");
        }
        }

        @PostMapping("/register")
    public  ResponseEntity<User> register(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = repo.save(user);

            // 3. Retour de l'objet avec le statut 201
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        }
    }

