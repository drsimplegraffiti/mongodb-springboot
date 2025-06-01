package com.drsimple.springbootmongoapp.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileResponseDTO {
    private String id;
    private String bio;
    private String website;
    private String userId;
}