package com.hisabaty.demo.Student;


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
