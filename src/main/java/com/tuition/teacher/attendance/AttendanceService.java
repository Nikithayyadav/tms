package com.tuition.teacher.attendance;

import org.springframework.stereotype.Service;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EnrollmentRepository enrollmentRepository;

    public AttendanceService(
            AttendanceRepository attendanceRepository,
            EnrollmentRepository enrollmentRepository) {

        this.attendanceRepository = attendanceRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public Attendance markAttendance(
            AttendanceRequest attendanceRequest) {

        boolean enrolled =
                enrollmentRepository
                        .existsByStudentIdAndBatchIdAndStatus(
                                attendanceRequest.getStudentId(),
                                attendanceRequest.getBatchId(),
                                "ACTIVE");

        if (!enrolled) {
            throw new StudentNotEnrolledException(
                    "Student is not actively enrolled in this batch");
        }

        boolean alreadyMarked =
                attendanceRepository
                        .existsByStudentIdAndBatchIdAndDate(
                                attendanceRequest.getStudentId(),
                                attendanceRequest.getBatchId(),
                                attendanceRequest.getDate());

        if (alreadyMarked) {
            throw new AttendanceAlreadyExistsException(
                    "Attendance already marked for this student on this date");
        }

        Attendance attendance = new Attendance();

        attendance.setTeacherId(attendanceRequest.getTeacherId());
        attendance.setBatchId(attendanceRequest.getBatchId());
        attendance.setStudentId(attendanceRequest.getStudentId());
        attendance.setDate(attendanceRequest.getDate());
        attendance.setPresent(attendanceRequest.isPresent());

        return attendanceRepository.save(attendance);
    }
}