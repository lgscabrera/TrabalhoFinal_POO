package org.example.academic.system.security;

import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

/**
 * Representa um usuário autenticado no sistema (US-2366).
 * A igualdade é definida pelo username (TUS-2382).
 */
@Getter
@ToString(exclude = "password")
public class User {

    private final String username;
    private final String password;
    private final Role role;

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Dois usuários são iguais se tiverem o mesmo username (TUS-2382).
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
