package com.example.jomajomadelivery.common;

import com.example.jomajomadelivery.exception.FileSaveException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Component
public class ImageHandler {
    public String save(MultipartFile img,String path) {
        String baseDir = new File("src/main/resources/static/images/"+path).getAbsolutePath() + File.separator;
        String fileName = UUID.randomUUID().toString()+ img.getOriginalFilename();
        String fullPathName = baseDir + fileName;
        String savePath = "/images/"+path+"/" + fileName;
        try {
            img.transferTo(new File(fullPathName));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new FileSaveException(e.getMessage());
        }
        return savePath;
    }
}
