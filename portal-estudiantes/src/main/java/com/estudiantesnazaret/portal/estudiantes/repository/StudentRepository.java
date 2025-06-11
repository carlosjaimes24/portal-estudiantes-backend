package com.estudiantesnazaret.portal.estudiantes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.estudiantesnazaret.portal.estudiantes.model.Student;
import java.util.Optional;
import com.estudiantesnazaret.portal.estudiantes.model.User;


public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);
}
