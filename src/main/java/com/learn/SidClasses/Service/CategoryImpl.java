package com.learn.SidClasses.Service;

import com.learn.SidClasses.Customs_Constants.PaginationConstants;
import com.learn.SidClasses.Dto.CategoryDto;
import com.learn.SidClasses.Entities.Category;
import com.learn.SidClasses.Exception.ResourceAlreadyExistException;
import com.learn.SidClasses.Exception.ResourceNotFoundException;
import com.learn.SidClasses.Repository.CategoryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryImpl implements CategoryService{
    private CategoryRepo carepo;
    private ModelMapper maper;
    public CategoryImpl(CategoryRepo carepo, ModelMapper maper) {
        this.carepo = carepo;
        this.maper = maper;
    }

    @Override
    public CategoryDto create(CategoryDto cadto) {
        if (carepo.existsByTitle(cadto.getTitle())){
            throw new ResourceAlreadyExistException("Sorry dear, The Category you tryna create with title:"+cadto.getTitle()+" already exists");
        }
        cadto.setAddeddate(new Date());
        Category savedcat=carepo.save(dtoToEntity(cadto));
        return entityToDto(savedcat);
    }

    @Override
    public CategoryDto update(CategoryDto cadto, UUID id) {
       Category cag=carepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Sorry dear, The Category you tryna update with id:"+id+" doesn't exist"));
       cag.setTitle(cadto.getTitle());
       cag.setDescription(cadto.getDescription());
       Category updatedcat= carepo.save(cag);
       return entityToDto(updatedcat);
    }

    @Override
    public void delete(UUID id) {
        Category cat=carepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Sorry dear, The Category you tryna delete with id:"+id+" doesn't exist"));
        carepo.deleteById(id);
    }

    //getall with pagination logic
    @Override
    public PaginationConstants<CategoryDto> getAll(int pno, int psize,String sortBy) {
        Sort sort=Sort.by(sortBy).ascending();
        PageRequest prq=PageRequest.of(pno,psize,sort); //pagination
        Page<Category> allcat=carepo.findAll(prq);
        List<CategoryDto> allcatdto=allcat.stream().map(cat -> entityToDto(cat)).collect(Collectors.toList());
        PaginationConstants<CategoryDto> pconst=new PaginationConstants<CategoryDto>();
        pconst.setPageNo(pno);
        pconst.setPageSize(psize);
        pconst.setTotalPages(allcat.getTotalPages());
        pconst.setTotalElements(allcat.getTotalElements());
        pconst.setContent(allcatdto);
        pconst.setLast(allcat.isLast());
        return pconst;
    }

    @Override
    public List<CategoryDto> getAll() {
       List<Category> allcat=carepo.findAll();
       List<CategoryDto> allcatdto=allcat.stream().map(cat -> entityToDto(cat)).collect(Collectors.toList());
       return allcatdto;
    }

    @Override
    public CategoryDto byId(UUID id) {
        Category cag=carepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Sorry dear, The Category you tryna fetch with id:"+id+" doesn't exist"));
       return entityToDto(cag);
    }

    @Override
    public CategoryDto byTitle(String tit) {
        Category cbt=carepo.findByTitleIgnoreCase(tit).orElseThrow(()->new ResourceNotFoundException("Sorry dear, The Category you tryna fetch with id doesn't exist"));
        return entityToDto(cbt);
    }

    public Category dtoToEntity(CategoryDto cdto){
       return maper.map(cdto,Category.class);
    }
    public CategoryDto entityToDto(Category category){
        return maper.map(category,CategoryDto.class);
    }
}
