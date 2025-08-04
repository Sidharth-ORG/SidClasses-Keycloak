package com.learn.SidClasses.Dto;

import com.learn.SidClasses.Entities.Category;
import com.learn.SidClasses.Entities.Video;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Data
public class CourseDto {

    private UUID id;

    @NotEmpty(message="Title can't be empty")
    @Size(min=2,message = "Title too small")
    private String title;

    @NotEmpty(message = "You need to brief it")
    @Size(min = 5,max = 50,message = "Length issues")
    private String brief;

    @NotEmpty(message = "You need to describe it")
    private String description;

    private boolean isLive;

    @NotEmpty(message = "You need to declare the price")
    private double price;

    private double discount;

    private Date createdDate;

    private String banner;

    private String bannerContentType;

    private List<VideoDto> videodto=new ArrayList<>();

    private List<CategoryDto> categorydto=new ArrayList<>();

    public String bannerurl(){
        return "http://localhost:8082/api/v1/courses/"+id+"/banners";
    }
}
