package com.learn.SidClasses.Service;

import com.learn.SidClasses.Customs_Constants.AppConstants;
import com.learn.SidClasses.Customs_Constants.PaginationConstants;
import com.learn.SidClasses.Customs_Constants.ResourceContentType;
import com.learn.SidClasses.Dto.CourseDto;
import com.learn.SidClasses.Dto.VideoDto;
import com.learn.SidClasses.Entities.Category;
import com.learn.SidClasses.Entities.Course;
import com.learn.SidClasses.Entities.Video;
import com.learn.SidClasses.Exception.ResourceAlreadyExistException;
import com.learn.SidClasses.Exception.ResourceNotFoundException;
import com.learn.SidClasses.Repository.CategoryRepo;
import com.learn.SidClasses.Repository.CourseRepo;
import com.learn.SidClasses.Repository.VideoRepo;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CourseImpl implements CourseService{

    private CourseRepo corepo;
    private ModelMapper mMaper;
    private CategoryRepo carepo;
    private FileImpl fimp;

    public CourseImpl(CourseRepo corepo, ModelMapper mMaper, CategoryRepo carepo,FileImpl fimp) {
        this.corepo = corepo;
        this.mMaper = mMaper;
        this.carepo=carepo;
        this.fimp = fimp;
    }

    @Override
    public CourseDto create(CourseDto cdt) {
        cdt.setLive(true);
        cdt.setCreatedDate(new Date());
       Course savedcourse= corepo.save(dtoToEntity(cdt));
       return entityToDto(savedcourse);
    }

    @Override
    public CourseDto update(CourseDto cdto, UUID id) {
        Course cr=corepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Doesn't exist"));
        cr.setTitle(cdto.getTitle());
        cr.setBrief(cdto.getBrief());
        cr.setDescription(cdto.getDescription());
        cr.setDiscount(cdto.getDiscount());
        Course updatedc= corepo.save(cr);
           return entityToDto(updatedc);
    }

    @Override
    public void delete(UUID id) {
        Course course=corepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course with id:"+id+" not found"));
        corepo.deleteById(id);

    }

    @Override
    public PaginationConstants<CourseDto> getAll(int pno,int psize,String sortby) {

        Sort srt=Sort.by(sortby).ascending();
        PageRequest prq= PageRequest.of(pno,psize,srt);
       Page<Course> allcourse=corepo.findAll(prq);
       List<CourseDto> allcdto=allcourse.stream().map(cr -> entityToDto(cr)).collect(Collectors.toList());
        PaginationConstants<CourseDto> pg=new PaginationConstants<>();
        pg.setPageNo(pno);
        pg.setPageSize(psize);
        pg.setTotalPages(allcourse.getTotalPages());
        pg.setTotalElements(allcourse.getTotalElements());
        pg.setContent(allcdto);
        pg.setLast(allcourse.isLast());
        return pg;
    }

    @Override
    public CourseDto byTitle(String title) {
      Course course= corepo.findByTitleIgnoreCase(title).orElseThrow(()-> new ResourceNotFoundException());
      CourseDto cdto=entityToDto(course);
        return cdto;
    }

    @Override
    public List<CourseDto> bykeyword(String key) {
       List<Course> cr= corepo.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(key,key);
       List<CourseDto> allcdto=cr.stream().map(crr -> entityToDto(crr)).collect(Collectors.toList());
        return allcdto;
    }

    @Override
    public void addCategoryToCourse(UUID courseid, UUID catid) {
       //getting course
        Course course=corepo.findById(courseid).orElseThrow(()->new ResourceNotFoundException("course not found"));
       //getting category
        Category cat=carepo.findById(catid).orElseThrow(()->new ResourceNotFoundException("Category not found"));
       //cat k andr courselist me course
       //course k andr cat h usme cat
        course.addCategory(cat);

        corepo.save(course);
        System.out.println("course relationship updated");

    }

    @Override
    public CourseDto saveBanner(MultipartFile mpf, UUID id) throws IOException {
      Course cr=corepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Sorry dear, Course not found !!!!!"));
      String fpath=fimp.fileupload(mpf,AppConstants.Default_filePath,mpf.getOriginalFilename());
        cr.setBanner(fpath);
        cr.setBannerContentType(mpf.getContentType());
        Course cent=corepo.save(cr);
        return entityToDto(cent);
    }

    @Override
    public ResourceContentType getBannerById(UUID id) {
        Course cr=corepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Sorry dear, Course not found !!!!!"));
        String path=cr.getBanner();
        Path fpath=Paths.get(path);
        Resource rsc=new FileSystemResource(fpath);
        ResourceContentType rct=new ResourceContentType();
        rct.setResource(rsc);
        rct.setContenttype(cr.getBannerContentType());
        return rct;
    }

//    @Override
//    public List<CourseDto> bycattitle(String title) {
//        List<Course> course=corepo.courseByCategoryTitleIgnoreCase(title);
//        List<CourseDto> cdto=course.stream().map(cr -> entityToDto(cr)).collect(Collectors.toList());
//        return cdto;
//    }


    //Mapping Dto values
    public CourseDto entityToDto(Course course){
          CourseDto etd= mMaper.map(course,CourseDto.class);          //instead of manually mapping we can use modelmapper
//        CourseDto cdto=new CourseDto();
//        cdto.setId(course.getId());
//        cdto.setTitle(course.getTitle());
//        cdto.setBrief(course.getBrief());
//        cdto.setDescription(course.getDescription());
//        cdto.setLive(course.isLive());
//        cdto.setPrice(course.getPrice());
//        cdto.setDiscount(course.getDiscount());
//        cdto.setCreatedDate(course.getCreatedDate());
//        cdto.setBanner(course.getBanner());
        return etd;
    }
    public Course dtoToEntity(CourseDto cdto){
          Course dte= mMaper.map(cdto,Course.class);
//        course.setId(cdto.getId());
//        course.setTitle(cdto.getTitle());
//        course.setBrief(cdto.getBrief());
//        course.setDescription(cdto.getDescription());
//        course.setLive(cdto.isLive());
//        course.setPrice(cdto.getPrice());
//        course.setDiscount(cdto.getDiscount());
//        course.setCreatedDate(cdto.getCreatedDate());
//        course.setBanner(cdto.getBanner());
        return dte;
    }
}
