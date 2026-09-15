package com.tuition.performance.repository;

import com.tuition.performance.entity.ExamName;
import com.tuition.performance.entity.Performance;
import com.tuition.performance.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {

    boolean existsByStudentIdAndExamNameAndSubject(
            Long studentId,
            ExamName examName,
            Subject subject
    );

    Page<Performance> findByStudentId(
            Long studentId,
            Pageable pageable
    );

    Page<Performance> findByStudentIdAndExamName(
            Long studentId,
            ExamName examName,
            Pageable pageable
    );

    Optional<Performance> findByStudentIdAndExamNameAndSubject(
            Long studentId,
            ExamName examName,
            Subject subject
    );
}