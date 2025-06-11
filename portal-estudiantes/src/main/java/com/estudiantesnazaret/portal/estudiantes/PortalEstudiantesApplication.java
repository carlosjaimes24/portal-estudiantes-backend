package com.estudiantesnazaret.portal.estudiantes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.estudiantesnazaret.portal.estudiantes.model")
public class PortalEstudiantesApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortalEstudiantesApplication.class, args);
	}

}
