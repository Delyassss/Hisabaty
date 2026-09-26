package com.hisabaty.demo;

import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExecptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<HashMap<String, String>> handleInvalidArgumentException(IllegalArgumentException ex)
    {
        return  ResponseEntity.body(Map.of("error", ex.getMessage()));
    }

    @ExecptionHandler(SchoolNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<HashMap<String, String>> handleSchoolNotFopund(SchoolNotFound  ex)
    {
        return  ResponseEntity.body(Map.of("error", ex.getMessage()));
    }

    @ExecptionHandler(ValueAlreadyExist.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<HashMap<String,String>> handleValueAlreadyExist(ValueAlreadyExist ex)
    {
        return ResponseEntity.body(Map.of("error", ex.getMessage()));
    }

    

    
}