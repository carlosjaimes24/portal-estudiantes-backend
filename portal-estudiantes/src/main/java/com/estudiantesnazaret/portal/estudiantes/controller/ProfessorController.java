package com.estudiantesnazaret.portal.estudiantes.controller;

import com.estudiantesnazaret.portal.estudiantes.model.Assignment;
import com.estudiantesnazaret.portal.estudiantes.model.Grade;
import com.estudiantesnazaret.portal.estudiantes.model.Student;
import com.estudiantesnazaret.portal.estudiantes.service.AssignmentService;
import com.estudiantesnazaret.portal.estudiantes.service.GradeService;
import com.estudiantesnazaret.portal.estudiantes.service.StudentService;
import com.estudiantesnazaret.portal.estudiantes.model.User;
import com.estudiantesnazaret.portal.estudiantes.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.estudiantesnazaret.portal.estudiantes.repository.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/api/professor")
public class ProfessorController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;



    @Autowired
    private AssignmentService assignmentService;

    @PreAuthorize("hasAuthority('PROFESSOR')")
    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PreAuthorize("hasAuthority('PROFESSOR')")
    @PostMapping("/students")
    public ResponseEntity<?> addStudent(@RequestBody Student student) {

        System.out.println("🧪 Entrando a addStudent como profesor");
        try {
            if (userRepository.findByEmail(student.getEmail()).isPresent()) {
                return ResponseEntity.status(409).body(Map.of("message", "El correo ya está registrado"));
            }

            Student savedStudent = studentService.saveStudent(student);


            String contraseñaGenerada = generarPasswordAleatoria(10);


            User user = new User();
            user.setEmail(student.getEmail());
            user.setPassword(passwordEncoder.encode(student.getPassword()));
            user.setRole("STUDENT");
            user.setName(student.getName());
            userService.save(user);


            return ResponseEntity.ok(Map.of(
                    "message", "Estudiante creado exitosamente",
                    "email", student.getEmail(),
                    "contraseñaTemporal", contraseñaGenerada

            ));



        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("message", "Error al crear el estudiante"));
        }
    }

    @PreAuthorize("hasAuthority('PROFESSOR')")
    @GetMapping("/assignments/download/{id}")
    public ResponseEntity<byte[]> descargarTareaComoProfesor(@PathVariable Long id) {
        Assignment tarea = assignmentService.getById(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + tarea.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(tarea.getFileType()))
                .body(tarea.getData());
    }



    @PreAuthorize("hasAuthority('PROFESSOR')")
    @GetMapping("/assignments")
    public ResponseEntity<List<Assignment>> getAllAssignments() {
        return ResponseEntity.ok(assignmentService.getAllAssignments());
    }


    @PreAuthorize("hasAuthority('PROFESSOR')")
    @DeleteMapping("/students/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Estudiante eliminado");
    }

    @PreAuthorize("hasAuthority('PROFESSOR')")
    @PostMapping("/grades")
    public ResponseEntity<Grade> addGrade(@RequestBody Grade grade) {
        return ResponseEntity.ok(gradeService.saveGrade(grade));
    }

    @PreAuthorize("hasAuthority('PROFESSOR')")
    @PutMapping("/assignments/{id}")
    public ResponseEntity<Assignment> gradeAssignment(@PathVariable Long id, @RequestParam Double score) {
        Assignment assignment = assignmentService.gradeAssignment(id, score);
        return ResponseEntity.ok(assignment);
    }



    private String generarPasswordAleatoria(int longitud) {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%&*!";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < longitud; i++) {
            int index = (int) (Math.random() * caracteres.length());
            password.append(caracteres.charAt(index));
        }
        return password.toString();
    }

}
