package com.tuition.teacher.attendance;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AttendanceRequest {

    private Long teacherId;

    private Long batchId;

    private Long studentId;

    private LocalDate date;

    private boolean present;
}