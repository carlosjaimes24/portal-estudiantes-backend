package com.estudiantesnazaret.portal.estudiantes.controller;

import com.estudiantesnazaret.portal.estudiantes.model.Assignment;
import com.estudiantesnazaret.portal.estudiantes.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assignments")
public class AssignmentController {
    @Autowired
    private AssignmentService assignmentService;

    @GetMapping
    public List<Assignment> getAssignments() {
        return assignmentService.getAllAssignments();
    }

    @PostMapping
    public Assignment createAssignment(@RequestBody Assignment assignment) {
        return assignmentService.saveAssignment(assignment);
    }
}
