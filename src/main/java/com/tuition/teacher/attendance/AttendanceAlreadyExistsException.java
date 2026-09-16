package com.tuition.teacher.attendance;

public class AttendanceAlreadyExistsException extends RuntimeException {

    public AttendanceAlreadyExistsException(String message) {
        super(message);
    }
}