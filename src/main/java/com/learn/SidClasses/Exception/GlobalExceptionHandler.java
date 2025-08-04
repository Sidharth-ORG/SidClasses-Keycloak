package com.learn.SidClasses.Exception;

import com.learn.SidClasses.Customs_Constants.CustomMesssage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
   @Autowired
   private CustomMesssage cmsg;

   @ExceptionHandler(ResourceNotFoundException.class)
   public ResponseEntity<CustomMesssage> ResourceNotFoundExceptionHandler(ResourceNotFoundException notFound){
      cmsg.setMessage(notFound.getMessage());
      cmsg.setSuccess(false);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(cmsg);
   }

   @ExceptionHandler(ResourceAlreadyExistException.class)
   public ResponseEntity<CustomMesssage> resourceAlreadyExistExceptionHandler(ResourceAlreadyExistException exists){
      cmsg.setMessage(exists.getMessage());
      cmsg.setSuccess(false);
      return ResponseEntity.status(HttpStatus.CONFLICT).body(cmsg);
   }

   //doubt in this
   @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<Map<String,String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException manv){
         Map<String,String> errors=new HashMap<>();
         manv.getBindingResult().getAllErrors().forEach(error->{
            String fieldName=((FieldError) error).getField();
            String errorMessage =error.getDefaultMessage();
            errors.put(fieldName,errorMessage);
         });
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
   }

   @ExceptionHandler
   public ResponseEntity<CustomMesssage> credentialNotFoundExceptionHandler(CredentialNotFound cnf){
      CustomMesssage cmsg=new CustomMesssage();
      cmsg.setMessage(cnf.getMessage());
      cmsg.setSuccess(false);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(cmsg);
   }


}
