package com.learn.SidClasses.Repository;

import com.learn.SidClasses.Entities.Category;
import com.learn.SidClasses.Entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepo extends JpaRepository<Category, UUID> {
    Optional<Category> findByTitleIgnoreCase(String title);
    Boolean existsByTitle(String tit);



}
