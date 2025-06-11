package com.estudiantesnazaret.portal.estudiantes.controller;

import com.estudiantesnazaret.portal.estudiantes.dto.LoginRequest;
import com.estudiantesnazaret.portal.estudiantes.dto.LoginResponse;
import com.estudiantesnazaret.portal.estudiantes.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.estudiantesnazaret.portal.estudiantes.model.User;
import com.estudiantesnazaret.portal.estudiantes.repository.UserRepository;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Intentando login con: " + loginRequest.getEmail());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();


            User user = userRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));


            String token = jwtUtil.generateToken(user.getEmail(), user.getRole());


            return ResponseEntity.ok(new LoginResponse(token, user.getRole(), "Login exitoso"));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }

}
