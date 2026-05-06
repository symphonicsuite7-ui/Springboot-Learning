package com.codewithmosh.store.service;

import com.codewithmosh.store.dtos.UserDto;
import com.codewithmosh.store.mappers.UserMapper;
import com.codewithmosh.store.repositories.UserRepository;
import lombok.Data;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Data
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
}
