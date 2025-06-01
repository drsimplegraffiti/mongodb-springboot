package com.drsimple.springbootmongoapp.controller;

import com.drsimple.springbootmongoapp.dtos.ProfileRequestDTO;
import com.drsimple.springbootmongoapp.dtos.ProfileResponseDTO;
import com.drsimple.springbootmongoapp.model.Profile;
import com.drsimple.springbootmongoapp.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping
    public ResponseEntity<ProfileResponseDTO> createProfile(@RequestBody ProfileRequestDTO profile) {
        ProfileResponseDTO createdProfile = profileService.createProfile(profile);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ProfileResponseDTO> getProfileByUser(@PathVariable String userId) {
        return ResponseEntity.ok(profileService.getProfileByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponseDTO> getProfile(@PathVariable String id) {
        return ResponseEntity.ok(profileService.getProfileById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable String id) {
        profileService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }
}

