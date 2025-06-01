package com.drsimple.springbootmongoapp.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileRequestDTO {
    @NotBlank(message = "Bio is required")
    private String bio;

    private String website;

    @NotBlank(message = "UserId is required")
    private String userId;
}