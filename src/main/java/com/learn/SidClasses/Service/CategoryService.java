package com.learn.SidClasses.Service;

import com.learn.SidClasses.Customs_Constants.PaginationConstants;
import com.learn.SidClasses.Dto.CategoryDto;
import com.learn.SidClasses.Entities.Category;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryDto create(CategoryDto cadto);
    CategoryDto update(CategoryDto cadto, UUID id);
    void delete (UUID id);
    PaginationConstants<CategoryDto> getAll(int pno, int psize,String sortBy);
    List<CategoryDto> getAll();
    CategoryDto byId(UUID id);
    CategoryDto byTitle(String tit);


}
