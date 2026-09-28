package com.hisabaty.demo.Student;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class StudentNotFound extends RuntimeException
{
        public StudentNotFound(String cin , Long id )
        {
            super("Error: Student [" + id + "] [CIN : " + cin + " ] not found.");
        }
        public StudentNotFound(Long id)
        {
            super("Error: Student [" + id + " ] not found.");
        }
         public StudentNotFound()
        {
            super("Error: Students not found.");
        }
        public StudentNotFound(String msg)
        {
            super(msg);
        }
}
