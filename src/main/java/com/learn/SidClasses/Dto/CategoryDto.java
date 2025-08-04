package com.learn.SidClasses.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.learn.SidClasses.Entities.Course;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class CategoryDto {
    private UUID id;

    @NotEmpty(message="Mandatory field,Can't be empty !!!")
    @Size(min=2,message = "Title too small")
    private String title;
    @Size(min=7,max = 500,message = "Not matching requirements !!!!")
//    @JsonIgnore to ignore while serialization to json
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/mm/yyyy hh:mm:ss a",timezone = "IST")
    private Date addeddate;

    private List<CourseDto> coursedto=new ArrayList<>();

}
