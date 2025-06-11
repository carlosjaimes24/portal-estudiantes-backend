package com.estudiantesnazaret.portal.estudiantes.service;

import com.estudiantesnazaret.portal.estudiantes.model.Student;
import com.estudiantesnazaret.portal.estudiantes.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;


    public Student getStudentByEmail(String email) {
       return studentRepository.findByEmail(email)
               .orElseThrow(() -> new RuntimeException("Estudiante no encontrado por email: " + email));
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}

