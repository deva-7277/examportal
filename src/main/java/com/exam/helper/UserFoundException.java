package com.exam.helper;

public class UserFoundException extends Exception{

    public UserFoundException() {
        super("User found with this username in database !!");
    }

    public UserFoundException(String msg) {
        super(msg);
    }
}
