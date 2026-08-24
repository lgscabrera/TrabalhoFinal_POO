package org.example.academic.system;

import org.example.academic.system.exception.AuthenticationException;
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
 * Testes de comportamento de autenticação (US-2386).
 */
@ExtendWith(MockitoExtension.class)
class AuthenticationTest {

    @Mock
    private UserRepository userRepository;

    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        // Garante sessão limpa antes de cada teste
        Session.getInstance().logout();
        authenticationService = new AuthenticationService(userRepository);
    }

    @Test
    @DisplayName("US-2386: Usuário com credenciais válidas deve autenticar com sucesso")
    void shouldAuthenticateWithValidCredentials() {
        User admin = new User("admin", "admin123", Role.ADMIN);
        when(userRepository.findByUsername("admin")).thenReturn(admin);

        User result = authenticationService.authenticate("admin", "admin123");

        assertNotNull(result);
        assertEquals("admin", result.getUsername());
        assertEquals(Role.ADMIN, result.getRole());
        assertTrue(authenticationService.isAuthenticated());
    }

    @Test
    @DisplayName("US-2386: Username inválido deve lançar AuthenticationException")
    void shouldThrowAuthenticationExceptionForInvalidUsername() {
        when(userRepository.findByUsername("invalido")).thenReturn(null);

        assertThrows(AuthenticationException.class,
            () -> authenticationService.authenticate("invalido", "qualquersenha"));
    }

    @Test
    @DisplayName("US-2386: Senha inválida deve lançar AuthenticationException")
    void shouldThrowAuthenticationExceptionForInvalidPassword() {
        User user = new User("professor", "senhaCorreta", Role.PROFESSOR);
        when(userRepository.findByUsername("professor")).thenReturn(user);

        assertThrows(AuthenticationException.class,
            () -> authenticationService.authenticate("professor", "senhaErrada"));
    }

    @Test
    @DisplayName("US-2386: Após logout, sessão deve estar encerrada")
    void shouldClearSessionAfterLogout() {
        User admin = new User("admin", "admin123", Role.ADMIN);
        when(userRepository.findByUsername("admin")).thenReturn(admin);

        authenticationService.authenticate("admin", "admin123");
        assertTrue(authenticationService.isAuthenticated());

        authenticationService.logout();
        assertFalse(authenticationService.isAuthenticated());
        assertNull(authenticationService.getCurrentUser());
    }
}
