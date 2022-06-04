package com.example.itenaryplanner.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, unique = true, nullable = false)
    private Long id;

    @NotNull
    @Email
    @Column
    private String username;

    @NotNull
    @Size(min = 8, max = 30, message = "Password too short or long")
    @Column
    private String password;

    @NotNull
    @Column
    private String fullName;

    @Column(name= "Picture")
    private String profilePicture;
}

