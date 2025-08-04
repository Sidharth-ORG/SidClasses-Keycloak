package com.learn.SidClasses.Service;

import com.learn.SidClasses.Customs_Constants.PaginationConstants;
import com.learn.SidClasses.Customs_Constants.ResourceContentType;
import com.learn.SidClasses.Dto.VideoDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface VideoService {
    VideoDto create(VideoDto vdto);
    PaginationConstants<VideoDto> getAll(int pno, int psize, String title);
    VideoDto update(VideoDto vdto, UUID id);
    void Delete(UUID id);
    VideoDto savevid(MultipartFile mpf,UUID id) throws IOException;
    ResourceContentType getVideoByID(UUID id);
}
