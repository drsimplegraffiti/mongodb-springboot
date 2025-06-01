package com.drsimple.springbootmongoapp.controller;

import com.drsimple.springbootmongoapp.dtos.UserRequestDTO;
import com.drsimple.springbootmongoapp.dtos.UserResponseDTO;
import com.drsimple.springbootmongoapp.model.User;
import com.drsimple.springbootmongoapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("/paginated")
    public Page<User> getUsers(
            @RequestParam Map<String, String> allParams
    ) {
        // Extract known pagination/sort params
        int page = allParams.containsKey("page") ? Integer.parseInt(allParams.remove("page")) : 0;
        int size = allParams.containsKey("size") ? Integer.parseInt(allParams.remove("size")) : 10;
        String sortBy = allParams.remove("sortBy");
        String sortDir = allParams.remove("sortDir");

        // allParams now only contains filters
        return userService.getUserPaginated(allParams, page, size, sortBy, sortDir);
    }
}
