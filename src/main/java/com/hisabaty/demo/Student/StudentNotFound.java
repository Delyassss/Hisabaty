package com.hisabaty.demo.Student;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class StudentNotFound extends RuntimeException
{
        public StudentNotFound(String cin)
        {
            super("Error: Student [CIN : " + cin + " ] not found.");
        }
        public StudentNotFound()
        {
            super("Error: Students not found.");
        }
}
