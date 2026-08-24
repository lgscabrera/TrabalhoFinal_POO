package org.example.academic.system.controller;

import org.example.academic.system.exception.AuthenticationException;
import org.example.academic.system.model.Role;
import org.example.academic.system.model.User;
import org.example.academic.system.security.AuthenticationService;

/**
 * TUS-2414 - Ponte entre a GUI JavaFX e o AuthenticationService.
 * Converte security.User → model.User para desacoplar a camada de apresentação.
 */
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Autentica o usuário e retorna um model.User (usado pela GUI JavaFX).
     */
    public User authenticate(String username, String password) throws AuthenticationException {
        org.example.academic.system.security.User secUser =
            authenticationService.authenticate(username, password);
        Role role = Role.valueOf(secUser.getRole().name());
        return new User(secUser.getUsername(), secUser.getPassword(), role);
    }
}
