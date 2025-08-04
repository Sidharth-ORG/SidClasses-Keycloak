package com.learn.SidClasses.Dto;

import com.learn.SidClasses.Entities.Course;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.UUID;

@Data
public class VideoDto{
    private UUID id;
    @NotEmpty(message="can't be empty")
    private String title;

    private String description;

    private String video;

    private String extension;

}
