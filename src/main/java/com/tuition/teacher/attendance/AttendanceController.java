package com.tuition.teacher.attendance;

import com.tuition.teacher.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping
    public ApiResponse<Attendance> markAttendance(
            @RequestBody AttendanceRequest attendanceRequest) {

        Attendance attendance =
                attendanceService.markAttendance(attendanceRequest);

        return new ApiResponse<>(
                true,
                attendance,
                null,
                null
        );
    }
}