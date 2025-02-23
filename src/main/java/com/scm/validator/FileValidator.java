package com.scm.validator;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile>{

    private static final Long MAX_FILE_SIZE = (long) (1024*1024*2); //2MB

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        
        if (file==null || file.isEmpty()) {

            // context.disableDefaultConstraintViolation();
            // context.buildConstraintViolationWithTemplate("File cannot be empty").addConstraintViolation();

            return true;
        } else if (file.getSize()>MAX_FILE_SIZE) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File size shoulbe less than 2MB").addConstraintViolation();
            
            return false;
        }

        // try {
        //     BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        // } catch (IOException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }


        return true;
    }

}
