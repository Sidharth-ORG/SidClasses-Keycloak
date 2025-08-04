package com.learn.SidClasses.Repository;

import com.learn.SidClasses.Entities.Course;
import com.learn.SidClasses.Entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRepo extends JpaRepository<Course, UUID> {
    Optional<Course> findByTitleIgnoreCase(String title);

    //    @Query("select c from Course c join fetch c.Category where  c.Category.title=:tit")
//    List<Course> courseByCategoryTitleIgnoreCase(@RequestParam("tit") String tit);
    List<Course> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String key,String ke);
}