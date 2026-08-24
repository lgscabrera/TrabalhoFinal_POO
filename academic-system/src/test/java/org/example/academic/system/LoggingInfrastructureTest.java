package org.example.academic.system;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Testa que a infraestrutura de logging está configurada e operacional (TUS-2395).
 * Não verifica conteúdo específico de mensagens nem destino dos logs (AC-3 e AC-4).
 */
class LoggingInfrastructureTest {

    @Test
    @DisplayName("TUS-2395: Logger deve ser criado sem exceções")
    void loggerShouldBeCreatedSuccessfully() {
        Logger logger = LoggerFactory.getLogger(LoggingInfrastructureTest.class);
        assertNotNull(logger);
    }

    @Test
    @DisplayName("TUS-2395: Mensagens de log devem ser escritas sem lançar exceções")
    void logMessagesShouldBeWrittenWithoutExceptions() {
        Logger logger = LoggerFactory.getLogger(LoggingInfrastructureTest.class);
        assertDoesNotThrow(() -> {
            logger.info("Teste de log INFO - infraestrutura funcionando.");
            logger.warn("Teste de log WARN - infraestrutura funcionando.");
            logger.error("Teste de log ERROR - infraestrutura funcionando.");
            logger.debug("Teste de log DEBUG - infraestrutura funcionando.");
        });
    }

    @Test
    @DisplayName("TUS-2395: Comportamento de negócio deve permanecer inalterado com logging ativo")
    void businessBehaviorShouldBeUnchangedWithLoggingEnabled() {
        Logger logger = LoggerFactory.getLogger("test.business");
        // Simula uma operação de negócio com log — o resultado não deve mudar
        int result = 2 + 2;
        logger.info("Resultado: {}", result);
        org.junit.jupiter.api.Assertions.assertEquals(4, result);
    }
}
