package com.learn.SidClasses.Service;

import com.learn.SidClasses.Customs_Constants.AppConstants;
import com.learn.SidClasses.Customs_Constants.PaginationConstants;
import com.learn.SidClasses.Customs_Constants.ResourceContentType;
import com.learn.SidClasses.Dto.VideoDto;
import com.learn.SidClasses.Entities.Video;
import com.learn.SidClasses.Exception.ResourceAlreadyExistException;
import com.learn.SidClasses.Exception.ResourceNotFoundException;
import com.learn.SidClasses.Repository.VideoRepo;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

//@EnableWebSecurity(debug = true)
@Service
public class VideoImpl implements VideoService{
   private ModelMapper mp;
   private VideoRepo vrepo;
   private FileImpl fimpl;
    public VideoImpl(ModelMapper mp,VideoRepo vrepo,FileImpl fimpl) {
        this.mp = mp;
        this.vrepo=vrepo;
        this.fimpl=fimpl;
    }
    public VideoDto entityToDto(Video vdo){
        return mp.map(vdo,VideoDto.class);
    }
    public Video dtoToEntity(VideoDto vdto){
        return mp.map(vdto, Video.class);
    }
    @Override
    public VideoDto create(VideoDto vdto) {
     //  vrepo.existsByTitle(vdto.getTitle()).orElseThrow(()->new ResourceAlreadyExistException("Sorry dear,This Video is already there!!!!"));
        Video vdo=vrepo.save(dtoToEntity(vdto));
        return entityToDto(vdo);
    }

    @Override
    public PaginationConstants<VideoDto> getAll(int pno, int psize,String title) {
        Sort sort=Sort.by(title);
       PageRequest pgr=PageRequest.of(pno,psize,sort.ascending());
        Page<Video> pv=vrepo.findAll(pgr);
        List<VideoDto> vdto=pv.stream().map(vdo->entityToDto(vdo)).collect(Collectors.toList());
        PaginationConstants<VideoDto> pc=new PaginationConstants<>();
        pc.setPageNo(pno);
        pc.setPageSize(psize);
        pc.setTotalElements(pv.getTotalElements());
        pc.setTotalPages(pv.getTotalPages());
        pc.setContent(vdto);
        pc.setLast(pv.isLast());
        return pc;
    }

    @Override
    public VideoDto update(VideoDto vdto, UUID id) {
        Video vid=vrepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Sorry dear, The Category you tryna update with id:"+id+" doesn't exist"));
        vid.setTitle(vdto.getTitle());
        vid.setDescription(vdto.getDescription());
        Video updatedvid= vrepo.save(vid);
        return entityToDto(updatedvid);
    }

    @Override
    public void Delete(UUID id) {
        vrepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Sorry dear,entered id doesn't exist"));
        vrepo.deleteById(id);
    }

    @Override
    public VideoDto savevid(MultipartFile mpf,UUID id) throws IOException {
        Video vd=vrepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Not found"));
        vd.setVideo(fimpl.fileupload(mpf, AppConstants.Default_filePath,mpf.getOriginalFilename()));
        vd.setExtension(mpf.getContentType());
        Video newvd=vrepo.save(vd);
        return entityToDto(newvd);
    }

    @Override
    public ResourceContentType getVideoByID(UUID id) {
        Video vd=vrepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Not found"));
        String path=vd.getVideo();
        Path fpath=Paths.get(path);
        Resource rsc= new FileSystemResource(fpath);
        ResourceContentType rsct=new ResourceContentType();
        rsct.setResource(rsc);
        rsct.setContenttype(vd.getExtension());
        return rsct;
    }

}
