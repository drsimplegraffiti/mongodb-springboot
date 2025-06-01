package com.drsimple.springbootmongoapp.service;

import com.drsimple.springbootmongoapp.dtos.UserRequestDTO;
import com.drsimple.springbootmongoapp.dtos.UserResponseDTO;
import com.drsimple.springbootmongoapp.exceptions.ResourceNotFoundException;
import com.drsimple.springbootmongoapp.model.User;
import com.drsimple.springbootmongoapp.repository.UserRepository;
import com.drsimple.springbootmongoapp.utils.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;


    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public UserResponseDTO createUser(UserRequestDTO request) {
        userRepository.findByEmail(request.getEmail())
                .ifPresent(existingUser -> {
                    throw new IllegalArgumentException("User with this email already exists");
                });
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .build();
        return toResponseDTO(userRepository.save(user));
    }

    public UserResponseDTO getUserById(String id) {
        return userRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }



    public Page<User> getUserPaginated(Map<String, String> filters, Integer page, Integer size, String sortBy, String sortDirection) {
        Pageable pageable = PaginationUtil.createPageable(page, size, sortBy, sortDirection);
        Query query = PaginationUtil.buildQueryWithFilterAndPageable(filters, pageable);

        List<User> users = mongoTemplate.find(query, User.class);
        long count = mongoTemplate.count(Query.of(query).limit(-1).skip(-1), User.class); // remove pagination to count

        return new PageImpl<>(users, pageable, count);
    }

    private UserResponseDTO toResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }


}