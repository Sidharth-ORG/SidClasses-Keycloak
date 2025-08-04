package com.learn.SidClasses.Service;

import com.learn.SidClasses.Customs_Constants.PaginationConstants;
import com.learn.SidClasses.Customs_Constants.ResourceContentType;
import com.learn.SidClasses.Dto.CourseDto;
import com.learn.SidClasses.Dto.VideoDto;
import com.learn.SidClasses.Entities.Category;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface CourseService {
    CourseDto create(CourseDto cdto);
    CourseDto update(CourseDto cdto, UUID id);
    void delete(UUID id);
    PaginationConstants<CourseDto> getAll(int pno, int psize,String sortby);
    CourseDto byTitle(String title);
    List<CourseDto> bykeyword(String key);
     void addCategoryToCourse(UUID courseid,UUID catid);
     CourseDto saveBanner(MultipartFile mpf,UUID id) throws IOException;
     ResourceContentType getBannerById(UUID id);



}
