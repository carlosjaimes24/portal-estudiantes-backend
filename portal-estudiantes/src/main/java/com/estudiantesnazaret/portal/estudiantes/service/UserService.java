package com.estudiantesnazaret.portal.estudiantes.service;

import com.estudiantesnazaret.portal.estudiantes.model.User;
import com.estudiantesnazaret.portal.estudiantes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }
}
