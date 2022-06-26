package com.reicraftscodes.springbootemployee.exceptions;

public class InvalidCompanyException extends RuntimeException {
    public InvalidCompanyException(String message) {
        super(message);
    }
}
