package com.estudiantesnazaret.portal.estudiantes.service;

import com.estudiantesnazaret.portal.estudiantes.model.Grade;
import com.estudiantesnazaret.portal.estudiantes.model.Student;
import com.estudiantesnazaret.portal.estudiantes.repository.GradeRepository;
import com.estudiantesnazaret.portal.estudiantes.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GradeService {
    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private StudentRepository studentRepository;

    public List<Grade> getGradesByStudentId(Long studentId) {
        return gradeRepository.findByStudentId(studentId);
    }


    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }

    public Grade saveGrade(Grade grade) {
        if (grade.getStudent() == null || grade.getStudent().getId() == null) {
            throw new RuntimeException("El estudiante no puede ser nulo");
        }

        // Recuperar el estudiante de la base de datos antes de asignar la calificación
        Student existingStudent = studentRepository.findById(grade.getStudent().getId())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        grade.setStudent(existingStudent);
        return gradeRepository.save(grade);
    }
}

