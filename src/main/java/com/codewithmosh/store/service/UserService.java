package com.codewithmosh.store.service;

import com.codewithmosh.store.common.BusinessException;
import com.codewithmosh.store.dtos.RegisterUserRequest;
import com.codewithmosh.store.dtos.UpdateUserRequest;
import com.codewithmosh.store.dtos.UserDto;
import com.codewithmosh.store.mappers.UserMapper;
import com.codewithmosh.store.repositories.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;

@Service
//@Data
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public Iterable<UserDto> getAllUsers(String sort) {

        if (!Set.of( "name", "email").contains(sort)){
            sort = "name";
        }
        return userRepository.findAll(Sort.by(sort))
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserDto getUserById(Long id) {

        var user = userRepository.findById(id).orElse(null);

        if(user == null){
           throw new BusinessException(404, "User not found");
        }

        return userMapper.toDto(user);

    }
    public UserDto createUser(RegisterUserRequest request) {

        var user = userMapper.toEntity(request);

        userRepository.save(user);

        return userMapper.toDto(user);
    }
    public UserDto updateUser(Long id,
                                              UpdateUserRequest  request){
        var user = userRepository.findById(id).orElse(null);

        if(user == null){
            throw new BusinessException(404, "User not found");
        }

        userMapper.update(request, user);
        userRepository.save(user);

        return userMapper.toDto(user);
    }
    public ResponseEntity<Void> deleteUser(Long id){
        var user = userRepository.findById(id).orElse(null);
        if (user == null){
            return ResponseEntity.notFound().build();
        }
        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }

}
