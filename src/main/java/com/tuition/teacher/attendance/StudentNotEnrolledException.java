package com.tuition.teacher.attendance;

public class StudentNotEnrolledException extends RuntimeException {

    public StudentNotEnrolledException(String message) {
        super(message);
    }
}