package com.tsm_authentication.exception;

public class WrongCredentialException extends RuntimeException{
    public WrongCredentialException(String message){
        super(message);
    }
}
