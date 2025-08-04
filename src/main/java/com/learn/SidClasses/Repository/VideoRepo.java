package com.learn.SidClasses.Repository;

import com.learn.SidClasses.Entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VideoRepo extends JpaRepository<Video, UUID> {
    List<Video> findByTitleIgnoreCase(String title);
    Optional<Video> existsByTitle(String title);
//    @Query("select v from Video v join fetch v.Course where v.Course.title=:tit")
//    List<Video> videoByCourseTitle(@RequestParam("tit") String tit);

}
