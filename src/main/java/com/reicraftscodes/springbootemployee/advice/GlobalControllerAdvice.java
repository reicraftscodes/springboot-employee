package com.reicraftscodes.springbootemployee.advice;

import com.reicraftscodes.springbootemployee.exceptions.CompanyNotFoundException;
import com.reicraftscodes.springbootemployee.exceptions.EmployeeNotFoundException;
import com.reicraftscodes.springbootemployee.exceptions.InvalidCompanyException;
import com.reicraftscodes.springbootemployee.exceptions.InvalidEmployeeException;
import com.reicraftscodes.springbootemployee.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEmployeeNotFoundException(EmployeeNotFoundException employeeNotFoundException) {
        return new ErrorResponse(employeeNotFoundException.getMessage(), HttpStatus.NOT_FOUND.name());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleCompanyNotFoundException(CompanyNotFoundException companyNotFoundException) {
        return new ErrorResponse(companyNotFoundException.getMessage(), HttpStatus.NOT_FOUND.name());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidEmployeeException(InvalidEmployeeException invalidEmployeeException) {
        return new ErrorResponse(invalidEmployeeException.getMessage(), HttpStatus.BAD_REQUEST.name());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidCompanyException(InvalidCompanyException invalidCompanyException) {
        return new ErrorResponse(invalidCompanyException.getMessage(), HttpStatus.BAD_REQUEST.name());
    }
}
