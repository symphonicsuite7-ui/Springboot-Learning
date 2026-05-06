package com.codewithmosh.store.controller;

import com.codewithmosh.store.dtos.RegisterUserRequest;
import com.codewithmosh.store.dtos.UpdateUserRequest;
import com.codewithmosh.store.dtos.UserDto;
import com.codewithmosh.store.mappers.UserMapper;
import com.codewithmosh.store.repositories.UserRepository;
import com.codewithmosh.store.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final UserService userService;

    @GetMapping("")
    public Iterable<UserDto> getAllUsers(
            @RequestParam (required = false,defaultValue = "",name = "sort") String sort
    ) {
        return userService.getAllUsers(sort);
    }
    @GetMapping("/{id}")

    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
       return userService.getUserById(id);
    }
    @PostMapping("")
    public ResponseEntity<UserDto> createUser(@RequestBody RegisterUserRequest request,

    UriComponentsBuilder uriBuilder

    ) {

      return userService.createUser(request, uriBuilder);

    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id,
                              @RequestBody UpdateUserRequest request) {
        return userService.updateUser(id, request);
    }
       @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
        }
}
