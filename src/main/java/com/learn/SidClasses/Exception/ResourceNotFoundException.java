package com.learn.SidClasses.Exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

public class ResourceNotFoundException extends RuntimeException{
      public ResourceNotFoundException(String str){
        super(str);
    }
      public ResourceNotFoundException(){
        super ("Resource Not Found");
    }


}
