package com.bikkadit.electronicstrore.service.impl;

import com.bikkadit.electronicstrore.exception.BadApiRequestException;
import com.bikkadit.electronicstrore.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private Logger logger= LoggerFactory.getLogger(FileServiceImpl.class);
    @Override
    public String uploadFile(MultipartFile file, String path) {

        String originalFilename=file.getOriginalFilename();
        logger.info("Filename:{}",originalFilename);

        String filename= UUID.randomUUID().toString();
        String extension =originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension= filename+extension;
        String fullPathWithName=path+ File.separator+fileNameWithExtension;
        if(extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {

        }
            else{
                throw new BadApiRequestException("File with this  " +extension+ "is not allowed");
        }

        return null;
    }

    @Override
    public InputStream getResource(String path, String name) {
        return null;
    }
}
