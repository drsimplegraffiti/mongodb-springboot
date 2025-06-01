package com.drsimple.springbootmongoapp.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "profiles")
public class Profile {
    @Id
    private String id;

    private String bio;

    private String website;

    private String userId;
}