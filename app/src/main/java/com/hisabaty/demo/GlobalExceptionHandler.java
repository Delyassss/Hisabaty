package com.hisabaty.demo;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hisabaty.demo.School.SchoolNotFound;
import com.hisabaty.demo.Student.StudentAttendaceLimitException;
import com.hisabaty.demo.Student.StudentNotFound;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.MethodArgumentNotValidException;


@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleInvalidArgumentException(IllegalArgumentException ex)
    {
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex)
    {
        return  ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(SchoolNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> handleSchoolNotFopund(SchoolNotFound  ex)
    {
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler (StudentNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> handleStudentNotFound(StudentNotFound  ex)
    {
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(ValueAlreadyExist.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Map<String,String>> handleValueAlreadyExist(ValueAlreadyExist ex)
    {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", ex.getMessage()));
    }


    @ExceptionHandler(StudentAttendaceLimitException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Map<String,String>> handleStudentAttendaceLimitException(StudentAttendaceLimitException ex)
    {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", ex.getMessage()));
    }



    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, List<String>>> handleConstraintViolationException(ConstraintViolationException ex)
    {
        Map<String , List<String>> res = new HashMap<>();

       ex.getConstraintViolations().forEach(data -> res.computeIfAbsent(data.getPropertyPath().toString(), k -> new ArrayList<>()).add(data.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    // Valid spring shit 
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, List<String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
    {
        List<FieldError> feild = ex.getFieldErrors();
        Map<String, List<String>> res = new HashMap<>();

        for (FieldError f : feild)
        {
            String key  = f.getField();

            res.computeIfAbsent(key, k -> new ArrayList<>()).add(f.getDefaultMessage());
        }    
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
       
    }


    

    
}