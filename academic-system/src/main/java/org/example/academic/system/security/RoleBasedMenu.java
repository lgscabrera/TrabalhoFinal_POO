package org.example.academic.system.security;

import org.example.academic.system.exception.InvalidMenuOptionException;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Gera e exibe menus sequenciais baseados no papel do usuário autenticado.
 * Implementa US-2378 (renderização dinâmica) e US-2380 (numeração sequencial por papel).
 */
public class RoleBasedMenu {

    /**
     * Retorna o mapa de opções do menu para o papel especificado.
     * A numeração é sequencial e específica por papel (US-2380, AC-3).
     *
     * @param role papel do usuário autenticado
     * @return mapa ordenado de número -> descrição da opção
     */
    public static Map<Integer, String> getMenuOptions(Role role) {
        Map<Integer, String> options = new LinkedHashMap<>();
        int index = 1;

        // Opções comuns a ambos os papéis
        options.put(index++, "Cadastrar avaliação em turma");
        options.put(index++, "Gerar relatório de avaliações por turma");
        options.put(index++, "Gerar relatório de peso das avaliações");

        if (role == Role.ADMIN) {
            // Opções exclusivas do ADMIN (US-2378, AC-2 e AC-3)
            options.put(index++, "Cadastrar turma");
            options.put(index++, "Salvar dados acadêmicos");
            options.put(index++, "Configurar tipo de persistência");
            options.put(index++, "Gerar relatório de configuração de persistência");
        }

        options.put(index++, "Logout");
        options.put(index, "Sair");

        return options;
    }

    /**
     * Exibe o menu no console para o papel especificado.
     *
     * @param role papel do usuário autenticado
     */
    public static void displayMenu(Role role) {
        System.out.println("\n========== MENU ==========");
        System.out.println("Papel: " + role);
        System.out.println("---------------------------");
        Map<Integer, String> options = getMenuOptions(role);
        options.forEach((num, desc) -> System.out.printf("%d. %s%n", num, desc));
        System.out.println("===========================");
        System.out.print("Escolha uma opção: ");
    }

    /**
     * Valida se a opção escolhida existe para o papel atual.
     * Lança InvalidMenuOptionException caso a opção não exista (US-2368, AC-2).
     *
     * @param option opção escolhida pelo usuário
     * @param role   papel do usuário autenticado
     */
    public static void validateOption(int option, Role role) {
        Map<Integer, String> options = getMenuOptions(role);
        if (!options.containsKey(option)) {
            throw new InvalidMenuOptionException(
                "Opção inválida: " + option + ". Escolha entre 1 e " + options.size() + "."
            );
        }
    }
}
