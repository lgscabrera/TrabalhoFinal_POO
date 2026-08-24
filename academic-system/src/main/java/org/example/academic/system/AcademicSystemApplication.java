package org.example.academic.system;

import org.example.academic.system.exception.AcademicSystemException;
import org.example.academic.system.exception.AuthenticationException;
import org.example.academic.system.exception.AuthorizationException;
import org.example.academic.system.exception.InvalidMenuOptionException;
import org.example.academic.system.model.AcademicSystem;
import org.example.academic.system.model.Role;
import org.example.academic.system.model.User;
import org.example.academic.system.controller.AcademicSystemController;
import org.example.academic.system.controller.AuthenticationController;
import org.example.academic.system.security.AuthenticationService;
import org.example.academic.system.security.RoleBasedMenu;
import org.example.academic.system.security.Session;
import org.example.academic.system.security.UserRepository;
import org.example.academic.system.repository.UserTxtRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Scanner;

/**
 * Ponto de entrada da aplicação via linha de comando (US-0000, US-2364).
 */
public class AcademicSystemApplication {

    private static final Logger logger = LoggerFactory.getLogger(AcademicSystemApplication.class);

    public static void main(String[] args) {
        logger.info("Iniciando Academic System.");
        AcademicSystem.getInstance();

        AuthenticationService authService = new AuthenticationService(new UserRepository());
        AuthenticationController authController = new AuthenticationController(authService);
        AcademicSystemController controller = new AcademicSystemController();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Login loop
            User user = null;
            while (user == null) {
                System.out.print("Usuário: ");
                String username = scanner.nextLine().trim();
                System.out.print("Senha: ");
                String password = scanner.nextLine().trim();
                try {
                    user = authController.authenticate(username, password);
                    System.out.println("Login realizado com sucesso! Bem-vindo, " + user.getUsername());
                } catch (AuthenticationException ex) {
                    System.out.println("Credenciais inválidas. Tente novamente.");
                }
            }

            // Convert model.Role → security.Role for menu
            org.example.academic.system.security.Role secRole =
                org.example.academic.system.security.Role.valueOf(user.getRole().name());

            // Menu loop
            boolean loggedIn = true;
            while (loggedIn) {
                RoleBasedMenu.displayMenu(secRole);
                try {
                    int option = Integer.parseInt(scanner.nextLine().trim());
                    RoleBasedMenu.validateOption(option, secRole);
                    Map<Integer, String> opts = RoleBasedMenu.getMenuOptions(secRole);
                    String optName = opts.get(option);

                    if (optName.contains("Cadastrar avaliação")) {
                        System.out.print("Código da turma: ");
                        String code = scanner.nextLine().trim();
                        System.out.print("Tipo (EXAM/PRACTICAL_ASSIGNMENT/SEMINAR/ASSIGNMENT): ");
                        String type = scanner.nextLine().trim();
                        System.out.print("Valor: ");
                        double value = Double.parseDouble(scanner.nextLine().trim());
                        System.out.print("Peso: ");
                        double weight = Double.parseDouble(scanner.nextLine().trim());
                        controller.registerAssessment(user, code, type, value, weight);
                        System.out.println("Avaliação registrada com sucesso.");
                    } else if (optName.contains("relatório de avaliações")) {
                        System.out.println(controller.generateClassSummaryReport(user));
                    } else if (optName.contains("relatório de peso")) {
                        System.out.println(controller.generateWeightReport(user));
                    } else if (optName.contains("Cadastrar turma")) {
                        System.out.print("Código: ");
                        String code = scanner.nextLine().trim();
                        System.out.print("Título: ");
                        String title = scanner.nextLine().trim();
                        controller.registerClass(user, code, title);
                        System.out.println("Turma cadastrada com sucesso.");
                    } else if (optName.contains("Salvar")) {
                        controller.saveData(user);
                        System.out.println("Dados salvos com sucesso.");
                    } else if (optName.contains("Configurar tipo")) {
                        System.out.print("Tipo (TXT/XML/JSON): ");
                        String type = scanner.nextLine().trim();
                        controller.configurePersistence(user, type);
                        System.out.println("Persistência configurada para " + type);
                    } else if (optName.contains("configuração de persistência")) {
                        System.out.println(controller.generatePersistenceReport(user));
                    } else if (optName.contains("Logout")) {
                        authService.logout();
                        loggedIn = false;
                        System.out.println("Logout realizado.");
                    } else if (optName.contains("Sair")) {
                        System.out.println("Encerrando o sistema. Até logo!");
                        System.exit(0);
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Entrada inválida. Digite um número.");
                } catch (InvalidMenuOptionException ex) {
                    System.out.println("Opção inválida: " + ex.getMessage());
                } catch (AuthorizationException ex) {
                    System.out.println("Acesso negado: " + ex.getMessage());
                } catch (AcademicSystemException ex) {
                    System.out.println("Erro: " + ex.getMessage());
                }
            }
        }
    }
}
