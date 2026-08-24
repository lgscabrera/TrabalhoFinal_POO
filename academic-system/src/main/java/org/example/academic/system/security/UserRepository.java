package org.example.academic.system.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Repositório de usuários carregado a partir de arquivo TXT (US-2366, AC-5).
 * Formato esperado: username:password:ROLE (uma por linha)
 */
public class UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);
    private static final String USERS_FILE = "/users.txt";

    private final Map<String, User> users = new HashMap<>();

    public UserRepository() {
        loadUsers();
    }

    private void loadUsers() {
        try (InputStream is = getClass().getResourceAsStream(USERS_FILE)) {
            if (is == null) {
                logger.warn("Arquivo de usuários '{}' não encontrado. Usando usuários padrão.", USERS_FILE);
                loadDefaultUsers();
                return;
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#")) continue;
                    String[] parts = line.split(":");
                    if (parts.length == 3) {
                        String username = parts[0].trim();
                        String password = parts[1].trim();
                        Role role = Role.valueOf(parts[2].trim().toUpperCase());
                        users.put(username, new User(username, password, role));
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Erro ao carregar usuários: {}", e.getMessage());
            loadDefaultUsers();
        }
    }

    private void loadDefaultUsers() {
        users.put("admin", new User("admin", "admin123", Role.ADMIN));
        users.put("professor", new User("professor", "prof123", Role.PROFESSOR));
        logger.info("Usuários padrão carregados.");
    }

    public User findByUsername(String username) {
        return users.get(username);
    }
}
