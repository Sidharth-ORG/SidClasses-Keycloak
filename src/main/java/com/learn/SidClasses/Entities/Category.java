package com.learn.SidClasses.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
//    @Column(nullable=false,columnDefinition = "VARCHAR(36)")
    @Column(nullable=false)
    private UUID id;

    @Column(nullable=false,unique=true)
    private String title;

    @Column(nullable = false,unique=true,length=500)
    private String description;

    private Date addeddate;



    @ManyToMany()
    private List<Course> courseList=new ArrayList<>();

    public void addCourse(Course course){
        courseList.add(course);
        course.getCategoryList().add(this);
    }


    public void removeCourse(Course cr){
        courseList.remove(cr);
        cr.getCategoryList().remove(this);
    }
}
