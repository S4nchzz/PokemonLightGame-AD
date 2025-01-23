package org.lightPoke;

import org.lightPoke.auth.Login;
import org.lightPoke.auth.Register;
import org.lightPoke.db.entity.Ent_Trainer;
import org.lightPoke.db.services.Svice_Trainer;
import org.lightPoke.log.LogManagement;
import org.lightPoke.menus.AdminTournamentMenu;
import org.lightPoke.menus.GeneralAdminMenu;
import org.lightPoke.menus.TrainerMenu;
import org.lightPoke.users.ATUser;
import org.lightPoke.users.TRUser;
import org.lightPoke.users.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

/**
 * Clase Game que se encarga de gestionar toda la interaccion
 * de un usuario invitado
 *
 * @author Iyan Sanchez da Costa
 */

@SpringBootApplication
public class Game implements CommandLineRunner {
    private static LogManagement log = LogManagement.getInstance();

    public static void main(String[] args) {
        SpringApplication.run(Game.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        mainMenu();
    }

    /**
     * Menu principal que servira para registrar
     * un usuario o iniciar sesion
     */
    private static void mainMenu() {
        System.out.println("------ Welcome to LightPoke ------");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit \n");
        System.out.print("Please select an option: ");

        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();

        while (choice != 1 && choice != 2 && choice != 3) {
            System.out.println("------ Welcome to LightPoke ------");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit \n");
            System.out.print("Please select an option: ");
            choice = sc.nextInt();

        }

        switch (choice) {
            case 1:
                TRUser user = register();
                if (user != null) {
                    Svice_Trainer trainerService = Svice_Trainer.getInstance();
                    Ent_Trainer trainer = trainerService.getTrainerByUsername(user.getUsername());
                    new TrainerMenu(trainer);
                }
                break;
            case 2:
                login();
                break;
            case 3:
                System.out.println("Exiting...");
                System.exit(1);
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    /**
     * Metodo que dado un ususario y contraseña pedidos por pantalla
     * se intentara crear usando el metodo login de Login.class
     * si el retorno de esta clase == null significa que el usuario no
     * se ha encontrado o ha habido algun error a la hora de iniciar
     * sesion, en en cambio si el retorno es un objeto != null
     * se comprobara el tipo de ese usuario y se instanciara un objeto
     * con su propio menu segun el tipo que tenga
     */
    private static void login () {
        Scanner sc = new Scanner(System.in);
        System.out.println("------ Login ------");

        System.out.print("Usuario: ");
        final String username = sc.next();

        System.out.print("Password: ");
        final String password = sc.next();

        Login log = Login.getInstance();

        User user = log.login(username, password);
        if (user != null) {
            System.out.println("Login successful!");

            switch (user.getRole()) {
                case 1 -> {
                    System.out.println("Logged as Trainer");

                    Svice_Trainer trainerService = Svice_Trainer.getInstance();
                    new TrainerMenu(trainerService.getTrainerByUsername(username));
                }

                case 2 -> {
                    System.out.println("Logged as Admin tournament");
                    new AdminTournamentMenu((ATUser) user);
                }

                case 3 -> {
                    System.out.println("Logged as General Admin");
                    new GeneralAdminMenu();
                }
            }
        } else {
            System.out.println("Usuario no encontrado, intentelo de nuevo");
            Game.login();
        }
    }

    /**
     * Metodo que permitira al usuario invitado poder registrarse
     * especificando el usuario y la contraseña, verificando si este
     * no existe.
     * @return Nuevo usuario de tipo entrenador
     */
    private static TRUser register() {
        Scanner sc = new Scanner(System.in);
        System.out.println("------ Registro ------");

        Register reg = Register.getInstance();
        String username;
        String password;

        boolean anErrorOccurs = false;
        TRUser user = null;
        do {
            if (anErrorOccurs) {
                System.out.println("El usuario ya existe, prueba con otro");
            }
            System.out.print("Usuario: ");
            username = sc.next();

            System.out.print("Password: ");
            password = sc.next();

            anErrorOccurs = true;
        } while ((user = (TRUser) reg.register(username, password, "TR")) == null);

        return user;
    }
}