package org.example.academic.system.security;

import org.example.academic.system.exception.AuthenticationException;
import org.example.academic.system.exception.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Serviço responsável por autenticação e autorização baseada em papéis (RBAC).
 * Implementa US-2366 e US-2369.
 * Registra eventos de segurança em log (TUS-2391, TUS-2392).
 */
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserRepository userRepository;
    private final Session session;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.session = Session.getInstance();
    }

    /**
     * Autentica um usuário com base em username e senha.
     * Lança AuthenticationException em caso de credenciais inválidas (US-2369, AC-1).
     * Senhas nunca são registradas em log (US-2366, AC-6).
     *
     * @param username nome de usuário
     * @param password senha
     * @return usuário autenticado
     */
    public User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user == null || !user.getPassword().equals(password)) {
            // TUS-2391: registra tentativa falha sem expor a senha
            logger.warn("Tentativa de login malsucedida para o usuário: '{}'", username);
            throw new AuthenticationException("Credenciais inválidas. Verifique o usuário e a senha.");
        }

        session.login(user);
        // TUS-2391: registra login bem-sucedido
        logger.info("Login realizado com sucesso. Usuário: '{}', Papel: {}", username, user.getRole());
        return user;
    }

    /**
     * Encerra a sessão atual (US-2379).
     * Registra o evento de logout em log (TUS-2391).
     */
    public void logout() {
        User user = session.getCurrentUser();
        if (user != null) {
            logger.info("Logout realizado. Usuário: '{}', Papel: {}", user.getUsername(), user.getRole());
        }
        session.logout();
    }

    /**
     * Verifica se o usuário atual possui o papel exigido para executar uma operação.
     * Lança AuthorizationException se o acesso for negado (US-2369, AC-2).
     * Registra falhas de autorização em log (TUS-2392).
     *
     * @param requiredRole papel necessário para a operação
     * @param operationName nome da operação (para fins de auditoria)
     */
    public void authorize(Role requiredRole, String operationName) {
        User user = session.getCurrentUser();

        if (user == null || user.getRole() != requiredRole) {
            String userInfo = (user != null) ? user.getRole().toString() : "SEM SESSÃO";
            // TUS-2392: registra falha de autorização
            logger.warn("Acesso negado. Papel atual: '{}'. Operação protegida: '{}'.", userInfo, operationName);
            throw new AuthorizationException(
                "Acesso negado. Esta operação requer o papel: " + requiredRole + "."
            );
        }

        // TUS-2392: registra acesso autorizado
        logger.info("Acesso autorizado. Usuário: '{}', Papel: {}, Operação: '{}'",
            user.getUsername(), user.getRole(), operationName);
    }

    /**
     * Verifica se o usuário atual é ADMIN.
     */
    public void requireAdmin(String operationName) {
        authorize(Role.ADMIN, operationName);
    }

    /**
     * Verifica se há sessão ativa (qualquer papel).
     */
    public boolean isAuthenticated() {
        return session.isAuthenticated();
    }

    /**
     * Retorna o usuário da sessão atual.
     */
    public User getCurrentUser() {
        return session.getCurrentUser();
    }
}
