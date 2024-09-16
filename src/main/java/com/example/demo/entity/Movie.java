package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="movie")
public class Movie {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "movieYear")
    private Integer year;

    private String title;

    private Boolean winner;

    @ManyToMany
    @JoinTable(
            name="movie_studio",
            joinColumns = @JoinColumn(name="movie_id"),
            inverseJoinColumns=@JoinColumn(name ="studio_id")
    )
    private Set<Studio> studios = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name="movie_producer",
            joinColumns = @JoinColumn(name="movie_id"),
            inverseJoinColumns=@JoinColumn(name ="producer_id")
    )
    private Set<Producer> producers = new HashSet<>();

}

