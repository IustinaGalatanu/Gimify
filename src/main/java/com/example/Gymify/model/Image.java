package com.example.Gymify.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@Table(name="images")
@ToString(exclude = "users")
@EqualsAndHashCode(exclude = "users")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;

    @OneToMany(mappedBy = "image")
    private List<User> users;
}
