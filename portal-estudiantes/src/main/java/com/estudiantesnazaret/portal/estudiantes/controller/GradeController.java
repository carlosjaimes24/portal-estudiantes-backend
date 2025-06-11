package com.estudiantesnazaret.portal.estudiantes.controller;

import com.estudiantesnazaret.portal.estudiantes.model.Grade;
import com.estudiantesnazaret.portal.estudiantes.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/grades")
public class GradeController {
    @Autowired
    private GradeService gradeService;

    @GetMapping
    public List<Grade> getAllGrades() {
        return gradeService.getAllGrades();
    }

    @PostMapping
    public Grade createGrade(@RequestBody Grade grade) {
        return gradeService.saveGrade(grade);
    }

    @GetMapping("/export")
    public void exportGradesToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=notas.csv");

        List<Grade> grades = gradeService.getAllGrades();  // usa el servicio como ya haces

        PrintWriter writer = response.getWriter();
        writer.println("estudiante,asignatura,nota");

        for (Grade grade : grades) {
            String studentName = grade.getStudent() != null ? grade.getStudent().getName() : "Desconocido";
            writer.printf("%s,%s,%.2f%n", studentName, grade.getSubject(), grade.getScore());
        }

        writer.flush();
        writer.close();
    }
}
