package com.learn.SidClasses.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    public String  fileupload(MultipartFile file,String path,String name) throws IOException;
}
