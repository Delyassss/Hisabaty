package com.hisabaty.demo;

import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@ServiceA
public class GlobalExceptionHandler
{
    @ExecptionHandler(value = "illegalArgumentExeption")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<HashMap<String, String>> handleInvalidArgumentException(IllegalArgumentException ex)
    {
        return  Map.of("error", ex.getMessage());
    }
    
    
}