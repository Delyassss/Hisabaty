package com.hisabaty.demo.School;

import org.springframework.http.ResponseEntity;


import org.springframework.http.HttpStatusCode;



public class SchoolNotFound extends RuntimeException
{

    public SchoolNotFound(Long id)
    {
        super("School not found with id : " + id); 
    }
    
    public static ResponseEntity<String>  WebPage(String name)
    {
        return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(name);
    }
} 