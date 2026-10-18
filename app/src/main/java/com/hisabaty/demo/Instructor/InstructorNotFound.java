package com.hisabaty.demo.Instructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InstructorNotFound extends RuntimeException {

    public InstructorNotFound(String cin, Long id) {
        super("Error: Instructor [" + id + "] [CIN : " + cin + " ] not found.");
    }

    public InstructorNotFound(Long id) {
        super("Error: Instructor [" + id + " ] not found.");
    }

    public InstructorNotFound() {
        super("Error: Instructors not found.");
    }

    public InstructorNotFound(String msg) {
        super(msg);
    }
}
