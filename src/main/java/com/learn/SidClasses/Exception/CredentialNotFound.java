package com.learn.SidClasses.Exception;

public class CredentialNotFound extends RuntimeException{
    public CredentialNotFound(String str) {
        super (str);
    }
    public CredentialNotFound() {
       super ("Credential not found");
    }

}
