package com.estudiantesnazaret.portal.estudiantes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.estudiantesnazaret.portal.estudiantes.model.Grade;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByStudentId(Long studentId);
}

