package com.codewithmosh.store.controller;

import com.codewithmosh.store.common.ApiResponse;
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
    //private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final UserService userService;

    @GetMapping("")
    public ApiResponse<Iterable<UserDto>> getAllUsers(
            @RequestParam (required = false,defaultValue = "",name = "sort") String sort
    ) {
        return ApiResponse.success(userService.getAllUsers(sort));
    }
    @GetMapping("/{id}")

    public ApiResponse<UserDto> getUserById(@PathVariable Long id) {
       return ApiResponse.success(userService.getUserById(id));
    }
    @PostMapping("")
    public ResponseEntity<ApiResponse<UserDto>> createUser(@RequestBody RegisterUserRequest request,

    UriComponentsBuilder uriBuilder

    ) {

        var userDto = userService.createUser(request);

        var uri = uriBuilder
                .path("/users/{id}")
                .buildAndExpand(userDto.getId())
                .toUri();

      return ResponseEntity
              .created(uri)
              .body(ApiResponse.success(userDto));

    }
    @PutMapping("/{id}")
    public ApiResponse<UserDto> updateUser(@PathVariable Long id,
                              @RequestBody UpdateUserRequest request) {
        return ApiResponse.success(userService.updateUser(id, request));
    }
       @DeleteMapping("/{id}")
        public ApiResponse<ResponseEntity<Void>> deleteUser(@PathVariable Long id){
        return ApiResponse.success(userService.deleteUser(id));
        }
}
