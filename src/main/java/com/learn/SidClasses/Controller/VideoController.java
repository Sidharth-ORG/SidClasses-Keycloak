package com.learn.SidClasses.Controller;

import com.learn.SidClasses.Customs_Constants.AppConstants;
import com.learn.SidClasses.Customs_Constants.CustomMesssage;
import com.learn.SidClasses.Customs_Constants.PaginationConstants;
import com.learn.SidClasses.Customs_Constants.ResourceContentType;
import com.learn.SidClasses.Dto.VideoDto;
import com.learn.SidClasses.Service.VideoImpl;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.function.EntityResponse;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/videos")
public class VideoController {
    private VideoImpl vimp;
    public VideoController(VideoImpl vimp) {
        this.vimp = vimp;
    }

    //create
    @PostMapping
    public ResponseEntity<VideoDto> create(@RequestBody VideoDto vdt){
       return  ResponseEntity.status(HttpStatus.CREATED).body(vimp.create(vdt));
    }

    //read
    @GetMapping
    public PaginationConstants<VideoDto> read(@RequestParam(value="pageno",required = false,defaultValue= AppConstants.Default_page_no)int pageno,
                                                @RequestParam(value="pagesize",required = false,defaultValue= AppConstants.Default_page_size)int pagesize,
                                                @RequestParam(value="sortby",required = false,defaultValue= AppConstants.Default_sortBy)String sortby)
    {
        return vimp.getAll(pageno,pagesize,sortby);
    }

    //update
    @PutMapping("/{id}")
    public VideoDto update(@RequestBody VideoDto vdto,@PathVariable UUID id){
       return vimp.update(vdto,id);
    }

    //delete
    @DeleteMapping("/{id}")
    public String del(@PathVariable UUID id){
        vimp.Delete(id);
        return "deleted";
    }

    //saving video api
    @PostMapping("/{id}/videos")
    public ResponseEntity<?> savevideo(@PathVariable UUID id, MultipartFile video) throws IOException {
        if (!(video.getContentType().equalsIgnoreCase("video/mp4") || video.getContentType().equalsIgnoreCase("video/mkv"))){
            CustomMesssage cmsg=new CustomMesssage();
            cmsg.setMessage(video.getContentType()+": Extension not allowed");
            cmsg.setSuccess(false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cmsg);
        }
       return ResponseEntity.ok().body(vimp.savevid(video,id));
    }

    //serving video by id
    @GetMapping("/{id}/videos")
    public ResponseEntity<Resource> vidbyid(@PathVariable UUID id){
        ResourceContentType rct=vimp.getVideoByID(id);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(rct.getContenttype())).body(rct.getResource());
    }


}
