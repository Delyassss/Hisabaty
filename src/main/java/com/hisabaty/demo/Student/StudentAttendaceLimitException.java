/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hisabaty.demo.Student;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)

public class StudentAttendaceLimitException extends RuntimeException {

    public StudentAttendaceLimitException(Long studentId)
    {
        super("Student " + studentId + " has reached the maximum number of days attended per week");
    }

}
