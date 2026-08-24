package org.example.academic.system.repository;

import org.example.academic.system.security.UserRepository;

/**
 * Alias de UserRepository para compatibilidade com o LoginScreenController.
 */
public class UserTxtRepository extends UserRepository {
    public UserTxtRepository() {
        super();
    }
}
