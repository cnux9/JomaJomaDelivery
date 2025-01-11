package com.example.jomajomadelivery.common;

import com.example.jomajomadelivery.exception.FileSaveException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Component
public class ImageHandler {
    public String save(MultipartFile img) {
        String baseDir = new File("src/main/resources/static/img/").getAbsolutePath() + File.separator;
        String fileName = img.getOriginalFilename() + UUID.randomUUID().toString();
        String fullPathName = baseDir + fileName;
        try {
            img.transferTo(new File(fullPathName));
        }catch (Exception e){
            throw new FileSaveException(e.getMessage());
        }
        return fullPathName;
    }
}
