package com.estudiantesnazaret.portal.estudiantes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.estudiantesnazaret.portal.estudiantes.model.Assignment;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByStudentId(Long studentId);
}
