package com.learn.SidClasses.Exception;

public class ResourceAlreadyExistException extends RuntimeException{
    public ResourceAlreadyExistException(String str) {
        super(str);
    }

    public ResourceAlreadyExistException() {
        super("Resource Already Exists");
    }
}
