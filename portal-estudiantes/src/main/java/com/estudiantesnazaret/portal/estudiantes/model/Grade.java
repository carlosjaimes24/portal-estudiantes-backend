    package com.estudiantesnazaret.portal.estudiantes.model;

    import jakarta.persistence.*;
    import lombok.Data;
    import com.fasterxml.jackson.annotation.JsonBackReference;

    @Entity
    @Data
    @Table(name = "grades")
    public class Grade {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String subject;

        @Column(nullable = false)
        private Double score;

        @ManyToOne
        @JoinColumn(name = "student_id")
        @JsonBackReference("student-grade")
        private Student student;


        public Grade() {}

        public Grade(String subject, Double score, Student student) {
            this.subject = subject;
            this.score = score;
            this.student = student;
        }
    }