package org.example.academic.system.model;

import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

/**
 * Re-export de User para o pacote model (usado pelos controllers JavaFX).
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
