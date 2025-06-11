    package com.estudiantesnazaret.portal.estudiantes.model;

    import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
    import jakarta.persistence.*;
    import lombok.Data;
    import com.fasterxml.jackson.annotation.JsonBackReference;


    @Entity
    @Data
    @Table(name = "assignments")
    public class Assignment {

        @Lob
        private byte[] data;

        private String fileName;
        private String fileType;


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String title;

        @Column(nullable = false)
        private String description;

        //@Column(nullable = false)
        //private String fileUrl;

        @Column
        private Double score;

        @ManyToOne
        @JoinColumn(name = "student_id", nullable = false)
        @JsonBackReference("student-assignment")
        private Student student;




        public Assignment() {}

        public Assignment(String title, String description, String fileUrl, Student student) {
            this.title = title;
            this.description = description;
            //this.fileUrl = fileUrl;
            this.student = student;
        }
    }
