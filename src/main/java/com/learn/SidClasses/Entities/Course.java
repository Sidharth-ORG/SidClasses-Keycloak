package com.learn.SidClasses.Entities;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Course {
    @Id
    @Column(nullable=false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable=false,unique = true)
    private String title;

    @Column(nullable=false)
    private String brief;

    @Column(nullable=false,unique = true,length = 500)
    private String description;

    @Column(nullable=false)
    private boolean isLive;

    @Column(nullable=false)
    private double price;

    private double discount;

//    @CreatedDate need to read on this
    private Date createdDate;

    private String banner;

    private String bannerContentType;

    //For video
    @OneToMany(mappedBy="course")
    private List<Video> videoList=new ArrayList<>();

    //For category
    @ManyToMany(mappedBy = "courseList",cascade = CascadeType.ALL)
    private List<Category> categoryList=new ArrayList<>();

    public void addCategory(Category cat){
       categoryList.add(cat);
       cat.getCourseList().add(this);
    }
    public void removeCategory(Category cat){
        categoryList.remove(cat);
        cat.getCourseList().remove(this);
    }

}
