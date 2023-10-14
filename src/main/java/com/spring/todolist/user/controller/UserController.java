package com.spring.todolist.user.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.spring.todolist.user.User;
import com.spring.todolist.user.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping
    public ResponseEntity create(@RequestBody User user){
        User byUsername = userRepository.findByUsername(user.getUsername());
        if (byUsername != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("usuario já criado");
        }

        String hashed = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
        user.setPassword(hashed);

        return ResponseEntity.ok(userRepository.save(user));
    }

    @GetMapping
    public ResponseEntity get(){
        List<User> all = userRepository.findAll();
        if (all.isEmpty()){
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("nenhum registro encontrado");
        }
        return ResponseEntity.ok().body(all);
    }
}
