package org.example.academic.system;

import org.example.academic.system.exception.AuthorizationException;
import org.example.academic.system.security.AuthenticationService;
import org.example.academic.system.security.Role;
import org.example.academic.system.security.Session;
import org.example.academic.system.security.User;
import org.example.academic.system.security.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes de comportamento de autorização baseada em papéis (US-2387).
 */
@ExtendWith(MockitoExtension.class)
class AuthorizationTest {

    @Mock
    private UserRepository userRepository;

    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        Session.getInstance().logout();
        authenticationService = new AuthenticationService(userRepository);
    }

    @Test
    @DisplayName("US-2387: ADMIN deve ser autorizado para operações administrativas")
    void adminShouldBeAuthorizedForAdminOperations() {
        User admin = new User("admin", "admin123", Role.ADMIN);
        when(userRepository.findByUsername("admin")).thenReturn(admin);
        authenticationService.authenticate("admin", "admin123");

        assertDoesNotThrow(
            () -> authenticationService.authorize(Role.ADMIN, "cadastrar turma")
        );
    }

    @Test
    @DisplayName("US-2387: PROFESSOR não deve ser autorizado para operações de ADMIN")
    void professorShouldNotBeAuthorizedForAdminOperations() {
        User professor = new User("prof", "prof123", Role.PROFESSOR);
        when(userRepository.findByUsername("prof")).thenReturn(professor);
        authenticationService.authenticate("prof", "prof123");

        assertThrows(AuthorizationException.class,
            () -> authenticationService.authorize(Role.ADMIN, "cadastrar turma")
        );
    }

    @Test
    @DisplayName("US-2387: Acesso não autorizado deve lançar AuthorizationException")
    void unauthorizedAccessShouldThrowAuthorizationException() {
        User professor = new User("prof", "prof123", Role.PROFESSOR);
        when(userRepository.findByUsername("prof")).thenReturn(professor);
        authenticationService.authenticate("prof", "prof123");

        AuthorizationException ex = assertThrows(AuthorizationException.class,
            () -> authenticationService.requireAdmin("salvar dados")
        );
        assertNotNull(ex.getMessage());
        assertFalse(ex.getMessage().isBlank());
    }

    @Test
    @DisplayName("US-2387: Sem sessão ativa, qualquer operação protegida deve negar acesso")
    void withoutSessionShouldDenyProtectedOperation() {
        // Nenhum login realizado
        assertThrows(AuthorizationException.class,
            () -> authenticationService.authorize(Role.ADMIN, "operação protegida")
        );
    }
}
