package com.learn.SidClasses.Controller;

import com.learn.SidClasses.Customs_Constants.AppConstants;
import com.learn.SidClasses.Customs_Constants.CustomMesssage;
import com.learn.SidClasses.Customs_Constants.PaginationConstants;
import com.learn.SidClasses.Customs_Constants.ResourceContentType;
import com.learn.SidClasses.Dto.CategoryDto;
import com.learn.SidClasses.Dto.CourseDto;
import com.learn.SidClasses.Dto.VideoDto;
import com.learn.SidClasses.Entities.Course;
import com.learn.SidClasses.Service.CourseImpl;
import com.learn.SidClasses.Service.FileImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/courses")
@RestController
public class CourseController {
    private CourseImpl crimpl;

    public CourseController(CourseImpl crimpl) {
        this.crimpl = crimpl;
    }

    //create
    @Operation(summary = "Create New Course",description = "Pass new course info to create a new course")
    @ApiResponse(responseCode = "201",description = "Course created Successfully")
    @PostMapping
    public ResponseEntity<CourseDto> create(@RequestBody CourseDto cdto) {
        CourseDto savedcdto = crimpl.create(cdto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedcdto);
    }

    //Update
    @PutMapping("/{id}")
    public CourseDto update(@PathVariable UUID id, @RequestBody CourseDto cdto) {
        return crimpl.update(cdto, id);
    }

    //Delete
    @DeleteMapping("/{id}")
    public String del(@PathVariable UUID id) {
        crimpl.delete(id);
        return "Deleted Successfully";
    }


    //get by title
    @GetMapping("/tit/{title}")
    public CourseDto bytit(@PathVariable String title) {
        return crimpl.byTitle(title);
    }

    //getall
    @GetMapping
    public PaginationConstants<CourseDto> getall(
            @RequestParam(value = "pageno", required = false, defaultValue = AppConstants.Default_page_no) int pageno,
            @RequestParam(value = "pagesize", required = false, defaultValue = AppConstants.Default_page_size) int pagesize,
            @RequestParam(value = "sortby", required = false, defaultValue = AppConstants.Default_sortBy) String sortby
    ) {
        return crimpl.getAll(pageno, pagesize, sortby);
    }

    //keyword
    @GetMapping("/key/{key}")
    public ResponseEntity<List<CourseDto>> key(@PathVariable String key) {
        return ResponseEntity.status(HttpStatus.OK).body(crimpl.bykeyword(key));
    }

    //transaction
    @PostMapping("/{courseid}/categories/{categoryid}")
    public ResponseEntity<CustomMesssage> addCategoryToCourse(@PathVariable UUID courseid, @PathVariable UUID categoryid) {
        crimpl.addCategoryToCourse(courseid, categoryid);
        CustomMesssage cmsg = new CustomMesssage();
        cmsg.setMessage("Course Updated");
        cmsg.setSuccess(true);
        return ResponseEntity.status(HttpStatus.OK).body(cmsg);
    }


    //image upload api
    @PostMapping("/{id}/banners")
    public ResponseEntity<?> docapi(@PathVariable UUID id, @RequestParam MultipartFile banner) throws IOException {
        if(!(banner.getContentType().equalsIgnoreCase("image/png")||banner.getContentType().equalsIgnoreCase("image/jpeg")))
        {
            CustomMesssage cmsg=new CustomMesssage();
            cmsg.setMessage(banner.getContentType()+": File not supported");
            cmsg.setSuccess(false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cmsg);
        }
        return ResponseEntity.ok(crimpl.saveBanner(banner, id));
    }



    //to access the image
//    @ResponseStatus(HttpStatus.OK) instead of responseentity we can directly use this annotation aswell
    @GetMapping("/{id}/banners")
    public ResponseEntity<Resource> servefile(@PathVariable UUID id){
        ResourceContentType rsct=crimpl.getBannerById(id);
        return  ResponseEntity.ok().contentType(MediaType.parseMediaType(rsct.getContenttype())).body(rsct.getResource());
    }

}
