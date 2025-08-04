package com.learn.SidClasses.Controller;

import com.learn.SidClasses.Customs_Constants.AppConstants;
import com.learn.SidClasses.Customs_Constants.PaginationConstants;
import com.learn.SidClasses.Dto.CategoryDto;
import com.learn.SidClasses.Service.CategoryImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@SecurityRequirement(name = "basicAuth")
@RequestMapping("/api/v1/categories")
//@CrossOrigin(origins = "http://localhost:5174/")  //for preflight request from diffrent origin
public class CategoryController {
    private CategoryImpl cimpl;
    public CategoryController(CategoryImpl cimpl) {
        this.cimpl = cimpl;
    }

    //getting all with pagination
    @GetMapping
    public PaginationConstants<CategoryDto> getWithPages(@RequestParam(value="pageno",required=false,defaultValue = AppConstants.Default_page_no)int pageno,
                                                         @RequestParam(value="pagesize",required =false,defaultValue = AppConstants.Default_page_size)int pagesize,
                                                         @RequestParam(value ="sortBy",required = false,defaultValue = AppConstants.Default_sortBy)String sortBy){
        return cimpl.getAll(pageno,pagesize,sortBy);
    }


//    //getall
//    @GetMapping
//    public List<CategoryDto> getall(){
//        return cimpl.getAll();
//    }


    //Create
    @PostMapping
//    public ResponseEntity<?> create(@Valid @RequestBody CategoryDto cdto,BindingResult br) //BindingResult extends error hence all the functionalities
    public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryDto cdto)
    {
//        if(br.hasErrors()){
//           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Input");
//        }
        CategoryDto savedcdto=cimpl.create(cdto);
        return ResponseEntity.status(201).body(savedcdto);
    }
    // Update
    @PutMapping("/{id}")
    public CategoryDto update(@PathVariable UUID id,@RequestBody CategoryDto cdto){
        return cimpl.update(cdto, id);
    }

    //delete
    @DeleteMapping("/{id}")
    public String delete(@PathVariable UUID id){
        cimpl.delete(id);
        return "The Category with Id:"+id+" deleted Succefully";
    }


    //getbyid
    @GetMapping("/{id}")
    public CategoryDto byId(@PathVariable(value = "id")UUID id){
        return cimpl.byId(id);
    }


    //get by title
    @GetMapping("/title/{title}")
    public CategoryDto byTitle(@PathVariable(value = "title")String title){
        return cimpl.byTitle(title);
    }


}
