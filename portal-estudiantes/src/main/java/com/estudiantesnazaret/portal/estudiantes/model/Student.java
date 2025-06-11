package com.estudiantesnazaret.portal.estudiantes.model;

    import jakarta.persistence.*;
    import lombok.Data;
    import com.fasterxml.jackson.annotation.JsonManagedReference;
    import java.util.List;

    @Entity
    @Data
    @Table(name = "students")
    public class Student {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String name;

        @Column(nullable = false, unique = true)
        private String email;

        private String password;


        //@Column(nullable = false)
        //private String password;


        @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonManagedReference("student-grade")
        private List<Grade> grades;

        @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonManagedReference("student-assignment")
        private List<Assignment> assignments;


        public Student() {}

        public Student(String name, String email) {
            this.name = name;
            this.email = email;
        }
    }
