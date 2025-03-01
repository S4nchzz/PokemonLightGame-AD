package org.lightPoke.menus;

import com.db4o.ObjectContainer;
import org.lightPoke.auth.Login;
import org.lightPoke.auth.Register;
import org.lightPoke.db.db4o.DB4oInstance;
import org.lightPoke.db.entity.Ent_Trainer;
import org.lightPoke.db.services.Svice_Trainer;
import org.lightPoke.users.ATUser;
import org.lightPoke.users.TRUser;
import org.lightPoke.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Scanner;

@Component
public class MainMenu {
    @Autowired
    private Svice_Trainer serviceTrainer;

    @Autowired
    private Register reg;

    @Autowired
    private AdminTournamentMenu adminInTournamentMenu;

    @Autowired
    private TrainerMenu trainerMenu;

    @Autowired
    private GeneralAdminMenu generalAdminMenu;

    public void openMainMenu() {
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
                    Ent_Trainer trainer = serviceTrainer.getTrainerByUsername(user.getUsername());
                    trainerMenu.openMenu(trainer);
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
    private void login () {
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
                    trainerMenu.openMenu(serviceTrainer.getTrainerByUsername(username));
                }

                case 2 -> {
                    System.out.println("Logged as Admin tournament");
                    adminInTournamentMenu.openMenu((ATUser) user);
                }

                case 3 -> {
                    System.out.println("Logged as General Admin");
                    generalAdminMenu.openMenu();
                }
            }
        } else {
            System.out.println("Usuario no encontrado, intentelo de nuevo");
            login();
        }
    }

    /**
     * Metodo que permitira al usuario invitado poder registrarse
     * especificando el usuario y la contraseña, verificando si este
     * no existe.
     * @return Nuevo usuario de tipo entrenador
     */
    private TRUser register() {
        Scanner sc = new Scanner(System.in);
        System.out.println("------ Registro ------");

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
