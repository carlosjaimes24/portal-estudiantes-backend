package com.estudiantesnazaret.portal.estudiantes.service;

import com.estudiantesnazaret.portal.estudiantes.model.Assignment;
import com.estudiantesnazaret.portal.estudiantes.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    public List<Assignment> getAssignmentsByStudentId(Long studentId) {
        return assignmentRepository.findByStudentId(studentId);
    }

    public Assignment getById(Long id) {
        return assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
    }


    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public Assignment saveAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    // 👇 Este es el nuevo método para calificar tareas
    public Assignment gradeAssignment(Long id, Double score) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        assignment.setScore(score);
        return assignmentRepository.save(assignment);
    }
}

