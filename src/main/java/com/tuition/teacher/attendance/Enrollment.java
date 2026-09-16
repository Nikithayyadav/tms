package com.tuition.teacher.attendance;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "enrollments")
@Data
public class Enrollment {

    @Id
    private Long id;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "batch_id")
    private Long batchId;

    private String status;
}