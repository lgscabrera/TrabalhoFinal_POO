package org.example.academic.system;

import org.example.academic.system.security.Role;
import org.example.academic.system.security.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes de igualdade para objetos de domínio identificáveis (TUS-2384).
 * Verifica que equals e hashCode funcionam corretamente para User.
 */
class IdentifiableObjectEqualityTest {

    @Test
    @DisplayName("TUS-2384: Dois Users com o mesmo username devem ser iguais")
    void twoUsersWithSameUsernameShouldBeEqual() {
        User user1 = new User("joao", "senha1", Role.PROFESSOR);
        User user2 = new User("joao", "senha2", Role.ADMIN); // senha e role diferentes

        assertEquals(user1, user2);
    }

    @Test
    @DisplayName("TUS-2384: Dois Users com o mesmo username devem ter o mesmo hashCode")
    void twoUsersWithSameUsernameShouldHaveSameHashCode() {
        User user1 = new User("joao", "senha1", Role.PROFESSOR);
        User user2 = new User("joao", "outraSenha", Role.ADMIN);

        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    @DisplayName("TUS-2384: Users com usernames diferentes não devem ser iguais")
    void usersWithDifferentUsernamesShouldNotBeEqual() {
        User user1 = new User("joao", "senha", Role.PROFESSOR);
        User user2 = new User("maria", "senha", Role.PROFESSOR);

        assertNotEquals(user1, user2);
    }

    @Test
    @DisplayName("TUS-2384: User deve funcionar corretamente em HashSet")
    void userShouldWorkCorrectlyInHashSet() {
        User user1 = new User("joao", "senha1", Role.PROFESSOR);
        User user2 = new User("joao", "senha2", Role.ADMIN);

        Set<User> set = new HashSet<>();
        set.add(user1);
        set.add(user2); // mesmo username, não deve ser adicionado novamente

        assertEquals(1, set.size());
        assertTrue(set.contains(user1));
    }
}
