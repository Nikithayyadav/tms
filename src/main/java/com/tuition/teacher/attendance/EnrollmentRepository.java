package com.tuition.teacher.attendance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    boolean existsByStudentIdAndBatchIdAndStatus(
            Long studentId,
            Long batchId,
            String status);
}