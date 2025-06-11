package com.estudiantesnazaret.portal.estudiantes.controller;

import com.estudiantesnazaret.portal.estudiantes.model.Assignment;
import com.estudiantesnazaret.portal.estudiantes.model.Grade;
import com.estudiantesnazaret.portal.estudiantes.service.AssignmentService;
import com.estudiantesnazaret.portal.estudiantes.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.estudiantesnazaret.portal.estudiantes.model.Student;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.estudiantesnazaret.portal.estudiantes.service.StudentService;
import org.springframework.web.multipart.MultipartFile;
import com.estudiantesnazaret.portal.estudiantes.security.JwtUtil;
import com.estudiantesnazaret.portal.estudiantes.repository.UserRepository;
import com.estudiantesnazaret.portal.estudiantes.repository.StudentRepository;
import com.estudiantesnazaret.portal.estudiantes.model.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;





import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private GradeService gradeService;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;


    @GetMapping("/assignments/download/{id}")
    @PreAuthorize("hasAuthority('STUDENT') or hasAuthority('PROFESSOR')")
    public ResponseEntity<byte[]> descargarTarea(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Assignment tarea = assignmentService.getById(id);

        System.out.println("ROL ENCONTRADO (BACKEND): " + user.getRole());



        if ("STUDENT".equalsIgnoreCase(user.getRole())) {
            Student student = studentRepository.findByEmail(user.getEmail())
                    .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
            if (!tarea.getStudent().getId().equals(student.getId())) {
                return ResponseEntity.status(403).build();
            }
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + tarea.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(tarea.getFileType()))
                .body(tarea.getData());
    }



    @PreAuthorize("hasAuthority('STUDENT')")
    @GetMapping("/grades")
    public ResponseEntity<List<Grade>> getGrades(Authentication authentication) {
        String email = authentication.getName(); // Email desde el token JWT

        Student student = studentService.getStudentByEmail(email);
        List<Grade> grades = gradeService.getGradesByStudentId(student.getId());

        return ResponseEntity.ok(grades);
    }

    @PostMapping("/assignments")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<?> subirTarea(
            @RequestParam("file") MultipartFile file,
            @RequestParam("description") String description,
            Authentication authentication) {

        try {
            String email = authentication.getName();
            Student student = studentService.getStudentByEmail(email); // ✅ muy importante

            // Validar si student es null
            if (student == null) {
                return ResponseEntity.badRequest().body("Estudiante no encontrado");
            }

            Assignment nuevaTarea = new Assignment();
            nuevaTarea.setTitle(description);
            nuevaTarea.setDescription(description);
            nuevaTarea.setStudent(student); // ✅ Esto es clave
            nuevaTarea.setData(file.getBytes());
            nuevaTarea.setFileName(file.getOriginalFilename());
            nuevaTarea.setFileType(file.getContentType());

            Assignment guardada = assignmentService.saveAssignment(nuevaTarea);
            return ResponseEntity.ok(guardada);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al subir tarea");
        }
    }





    // ✅ Ver todas las tareas (opcional)
    @GetMapping("/assignments")
    @PreAuthorize("hasAuthority('STUDENT')")
    public List<Assignment> getAssignmentsForStudent(Authentication authentication) {
        String email = authentication.getName();
        Student student = studentService.getStudentByEmail(email);
        return assignmentService.getAssignmentsByStudentId(student.getId());
    }


}
