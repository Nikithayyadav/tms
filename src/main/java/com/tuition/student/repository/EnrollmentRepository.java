package com.tuition.student.repository;

import com.tuition.student.entity.Enrollment;
import com.tuition.student.entity.enums.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.tuition.student.entity.enums.EnrollmentStatus;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByStudentId(Long studentId);

    List<Enrollment> findByBatchId(Long batchId);

    List<Enrollment> findByStatus(EnrollmentStatus status);

    boolean existsByStudentIdAndBatchId(Long studentId, Long batchId);

    boolean existsByStudentIdAndBatchIdAndStatus(
            Long studentId,
            Long batchId,
            EnrollmentStatus status);
}
