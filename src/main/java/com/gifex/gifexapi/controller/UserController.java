package com.gifex.gifexapi.controller;

import com.gifex.gifexapi.controller.request.CreateUserDTO;
import com.gifex.gifexapi.models.ERole;
import com.gifex.gifexapi.models.RoleEntity;
import com.gifex.gifexapi.models.UserEntity;
import com.gifex.gifexapi.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/user/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO createUserDTO){

        Set<RoleEntity> roles = createUserDTO.getRoles().stream().map(
                role -> RoleEntity.builder()
                        .name(ERole.valueOf(role))
                        .build()
        ).collect(Collectors.toSet());
        UserEntity.UserEntityBuilder builder = UserEntity.builder();
        builder.username(createUserDTO.getUsername());
        builder.password(passwordEncoder.encode(createUserDTO.getPassword()));
        builder.email(createUserDTO.getEmail());
        builder.roles(roles);
        UserEntity userEntity = builder
                .build();
        userRepository.save(userEntity);
        return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping("/user/delete")
    public ResponseEntity<?> deleteUser(@RequestParam String id){
        userRepository.deleteById(Long.parseLong(id));
        return ResponseEntity.ok("user deleted id:".concat(id));
    }
}
