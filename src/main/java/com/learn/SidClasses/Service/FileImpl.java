package com.learn.SidClasses.Service;

import org.modelmapper.internal.bytebuddy.asm.Advice;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileImpl implements FileService{

    @Override
    public String fileupload(MultipartFile file, String path, String name) throws IOException {
        //storing the path
        Path fpath=Paths.get(path);
//checks if the directory (fpath) exists else creates the entire folder structure if not exist -createDirectories will create the path if not exists already createDirectory will not do that
        Files.createDirectories(fpath);
//add the filepath with filename
        Path filepath= Paths.get(fpath.toString(),file.getOriginalFilename());
        System.out.println(filepath);
//file writes
        Files.copy(file.getInputStream(),filepath,StandardCopyOption.REPLACE_EXISTING);
        return filepath.toString();
    }
}
