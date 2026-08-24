package org.example.academic.system.security;

/**
 * Gerencia a sessão do usuário autenticado (US-2366, US-2379).
 * Implementada como Singleton para garantir uma única sessão ativa.
 */
public class Session {

    private static Session instance;
    private User currentUser;

    private Session() {}

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    /**
     * Inicia uma sessão para o usuário autenticado.
     */
    public void login(User user) {
        this.currentUser = user;
    }

    /**
     * Encerra a sessão atual (US-2379).
     */
    public void logout() {
        this.currentUser = null;
    }

    /**
     * Retorna o usuário atualmente autenticado, ou null se não houver sessão.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Verifica se há uma sessão ativa.
     */
    public boolean isAuthenticated() {
        return currentUser != null;
    }

    /**
     * Retorna o papel (role) do usuário atual, se autenticado.
     */
    public Role getCurrentRole() {
        if (currentUser == null) {
            return null;
        }
        return currentUser.getRole();
    }
}
