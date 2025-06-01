package com.drsimple.springbootmongoapp.service;

import com.drsimple.springbootmongoapp.dtos.ProfileRequestDTO;
import com.drsimple.springbootmongoapp.dtos.ProfileResponseDTO;
import com.drsimple.springbootmongoapp.exceptions.ResourceNotFoundException;
import com.drsimple.springbootmongoapp.model.Profile;
import com.drsimple.springbootmongoapp.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileResponseDTO getProfileByUserId(String userId) {
        return profileRepository.findByUserId(userId)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found for user"));
    }

    public ProfileResponseDTO createProfile(ProfileRequestDTO request) {
        Profile profile = Profile.builder()
                .bio(request.getBio())
                .website(request.getWebsite())
                .userId(request.getUserId())
                .build();

        return toResponseDTO(profileRepository.save(profile));
    }

    public ProfileResponseDTO getProfileById(String id) {
        return profileRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));
    }

    public void deleteProfile(String id) {
        if (!profileRepository.existsById(id)) {
            throw new ResourceNotFoundException("Profile not found");
        }
        profileRepository.deleteById(id);
    }

    private ProfileResponseDTO toResponseDTO(Profile profile) {
        return ProfileResponseDTO.builder()
                .id(profile.getId())
                .bio(profile.getBio())
                .website(profile.getWebsite())
                .userId(profile.getUserId())
                .build();
    }
}