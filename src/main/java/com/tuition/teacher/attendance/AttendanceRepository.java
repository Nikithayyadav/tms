package com.tuition.teacher.attendance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    boolean existsByStudentIdAndBatchIdAndDate(
            Long studentId,
            Long batchId,
            LocalDate date);
}