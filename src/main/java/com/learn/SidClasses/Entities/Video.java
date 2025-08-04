package com.learn.SidClasses.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Entity
public class Video {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    @Column(unique=true,nullable=false)
    private String title;

    @Column(unique = true,nullable=false,length=500)
    private String description;

    @Column(nullable = false)
    private String video;

    @Column(nullable = false)
    private String extension;

    @ManyToOne
    private Course course;

}
