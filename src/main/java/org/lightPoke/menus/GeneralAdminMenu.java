package org.lightPoke.menus;

import org.lightPoke.auth.Register;
import org.lightPoke.db.db4o.entities.UserEnt_db4o;
import org.lightPoke.db.db4o.services.UserService_db4o;
import org.lightPoke.db.mongo.collections.TournamentCollection;
import org.lightPoke.db.mongo.collections.models.CombatModel;
import org.lightPoke.db.mongo.mapper.TournamentMapper;
import org.lightPoke.db.mongo.services.TournamentMongoService;
import org.lightPoke.db.mysql.entity.Ent_Combat;
import org.lightPoke.db.mysql.entity.Ent_Tournament;
import org.lightPoke.db.mysql.entity.Ent_Trainer;
import org.lightPoke.db.mysql.services.Svice_Tournament;
import org.lightPoke.log.LogManagement;
import org.lightPoke.tournament.Tournament;
import org.lightPoke.tournament.TournamentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

/**
 * Clase que solo sera accesible cuando un usuario de tipo
 * Administrador general (AGUser) inicia sesion permitiendo
 * poder crear un torneo.
 *
 * @author Iyan Sanchez da Costa
 */
@Component
public class GeneralAdminMenu {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private MainMenu mainMenu;

    @Autowired
    private Svice_Tournament serviceTournament;

    @Autowired
    private AdminTournamentMenu adminTMenu;

    @Autowired
    private Register register;

    @Autowired
    private UserService_db4o userServiceDb4o;

    @Autowired
    private TournamentMongoService tournamentMongoService;

    public GeneralAdminMenu() {}

    public void openMenu() {
        System.out.println("------ General Admin menu ------");
        System.out.println("1. Registrar nuevo torneo");
        System.out.println("2. Gestionar usuarios");
        System.out.println("3. Gestion torneos");
        System.out.println("4. Logout \n");
        System.out.print("Please select an option: ");

        Scanner sc = new Scanner(System.in);
        int choice;
        while((choice = sc.nextInt()) < 0 || choice > 4) {
            System.out.println("------ General Admin menu ------");
            System.out.println("1. Registrar nuevo torneo");
            System.out.println("2. Gestionar usuarios");
            System.out.println("3. Logout \n");
            System.out.print("Please select an option: ");
        }

        switch (choice) {
            case 1 -> {
                createTournament();
            }

            case 2 -> {
                userManagement();
            }

            case 3 -> {
                tournamentManagement();;
            }

            case 4 -> {
                mainMenu.openMainMenu();
            }
        }

        openMenu();
    }

    private void tournamentManagement() {
        Scanner sc = new Scanner(System.in);
        System.out.println("------ Gestion de torneos ------");
        System.out.println("1. Ver info torneo (Nombre, Region)");
        System.out.println("2. Mostrar ganador ganador de un torneo");
        System.out.println("3. Mostrar entrenadores que mas torneos han ganado");
        System.out.println("4. Listar todos los entrenadores con puntos");
        System.out.println("5. Mostrar puntos de un entrenador");
        System.out.println("6. Mostrar torneos de una region");
        System.out.println("7. Salir \n");
        System.out.print("Please select an option: ");

        int choice;
        while((choice = sc.nextInt()) < 0 || choice > 4) {
            System.out.println("------ Gestion de torneos ------");
            System.out.println("1. Ver info torneo (Nombre, Region)");
            System.out.println("2. Mostrar ganador de un torneo");
            System.out.println("3. Mostrar los 2 entrenadores que mas torneos han ganado");
            System.out.println("4. Listar todos los entrenadores con puntos");
            System.out.println("5. Mostrar puntos de un entrenador");
            System.out.println("6. Mostrar torneos de una region");
            System.out.println("7. Salir \n");
            System.out.print("Please select an option: ");
        }

        switch (choice) {
            case 1 -> {
                showTournamentInfo();
            }

            case 2 -> {
                showTournamentWinner();
            }

            case 3 -> {
                showTwoMoreTournamentWinners();
            }

            case 4 -> {
                showAlTrainerPoints();
            }

            case 5 -> {
                showSpecifiTrainerPoints();
            }

            case 6 -> {
                showTournamentsByRegion();
            }
            
            case 7 -> {
                openMenu();
            }
        }


        tournamentManagement();
    }

    private void showTournamentInfo() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Nombre del torneo: ");
        final String tournamentName = sc.next();

        System.out.println("Region del torneo: ");
        final char tournamentRegion = sc.next().charAt(0);

        TournamentCollection tournament = tournamentMongoService.findByNameAndRegion(tournamentName, tournamentRegion);

        if (tournament == null) {
            System.out.println("No se ha encontrado el torneo...");
            return;
        }

        System.out.println("Nombre: " + tournament.getName());
        System.out.println("Region: " + tournament.getRegion());
        System.out.println("Puntos de victoria: " + tournament.getVictory_points());
        System.out.println("Ganador: " + tournament.getT_winner().getUsername());

        int i = 1;

        System.out.println("Combates: ");
        for (CombatModel c : tournament.getCombats()) {
            System.out.println(" -Combate " + i);

            System.out.println("    Fecha: " + c.getDate());

            if (c.getTrainer_1() != null) {
                System.out.println("    Entrenador 1: " + c.getTrainer_1().getUsername());
            } else {
                System.out.println("    Entrenador 1: ?");
            }

            if (c.getTrainer_2() != null) {
                System.out.println("    Entrenador 2: " + c.getTrainer_2().getUsername());
            } else {
                System.out.println("    Entrenador 2: ?");
            }

            if (c.getC_winner() != null) {
                System.out.println("    Ganador: " + c.getC_winner().getUsername());
            } else {
                System.out.println("    Ganador: ?");
            }

            i++;
        }
    }

    private void showTournamentWinner() {

    }

    private void showTwoMoreTournamentWinners() {

    }

    private void showAlTrainerPoints() {

    }

    private void showSpecifiTrainerPoints() {


    }

    private void showTournamentsByRegion() {

    }

    private void userManagement() {
        Scanner sc = new Scanner(System.in);
        System.out.println("------ Gestion de usuarios ------");
        System.out.println("1. Listar usuarios");
        System.out.println("2. Modificar credenciales");
        System.out.println("3. Eliminar usuario");
        System.out.println("4. Salir \n");
        System.out.print("Please select an option: ");

        int choice;
        while((choice = sc.nextInt()) < 0 || choice > 4) {
            System.out.println("------ Gestion de usuarios ------");
            System.out.println("1. Listar usuarios");
            System.out.println("2. Modificar credenciales");
            System.out.println("3. Eliminar usuario");
            System.out.println("4. Salir \n");
            System.out.print("Please select an option: ");
        }

        switch (choice) {
            case 1 -> {
                listUsers();
            }

            case 2 -> {
                modifyUserCredentials();
            }

            case 3 -> {
                removeUser();
            }

            case 4 -> {
                openMenu();
            }
        }


        userManagement();
    }

    private void listUsers() {
        for (UserEnt_db4o u : userServiceDb4o.findAllUsers()) {
            System.out.println(u.getUsername() + " | " + u.getType());
        }
    }

    private void modifyUserCredentials() {
        Scanner sc = new Scanner(System.in);

        List<UserEnt_db4o> users = userServiceDb4o.findAllUsers();

        int position = 0;
        int choiceA = -1;

        while (choiceA < 0 || choiceA > users.size()) {
            for (UserEnt_db4o u : users) {
                System.out.println(position + " | " + u.getUsername() + " | " + u.getType());
                position++;
            }

            System.out.print("Seleccione un usuario a modificar: ");
            choiceA = sc.nextInt();
        }

        System.out.println("1. Modificar usuario");
        System.out.println("2. Modificar contraseña");
        int choiceB;
        while ((choiceB = sc.nextInt()) < 0 || choiceB > 2) {
            System.out.println("1. Modificar usuario");
            System.out.println("2. Modificar contraseña");
        }

        switch (choiceB) {
            case 1 -> {
                System.out.print("Nuevo usuario: ");
                final String newUsername = sc.next();

                userServiceDb4o.modifyUsername(users.get(choiceA).getUsername(), newUsername);
            }

            case 2 -> {
                System.out.print("Nueva contraseña: ");
                final String newPassword = sc.next();

                userServiceDb4o.modifyPassword(users.get(choiceA).getUsername(), newPassword);
            }
        }
    }

    private void removeUser() {
        Scanner sc = new Scanner(System.in);

        List<UserEnt_db4o> users = userServiceDb4o.findAllUsers();

        int choice = -1;
        int position = 0;

        while (choice < 0 || choice > users.size()) {
            for (UserEnt_db4o u : users) {
                System.out.println(position + " | " + u.getUsername() + " | " + u.getType());
                position++;
            }

            System.out.print("Seleccione un usuario a eliminar: ");
            choice = sc.nextInt();
        }

        userServiceDb4o.removeUser(users.get(choice).getUsername());
    }

    /**
     * Metodo que sera llamado en la fase de eleccion de opcion del menu
     * permitiendo crear un torneo con un nombre, codigo de region y
     * puntos maximos de victoria personalizados.
     */
    private void createTournament() {
        Scanner sc = new Scanner(System.in);
        System.out.println("------ Creacion de torneo ------");

        System.out.print("Nombre del torneo: ");
        String tName = sc.next();

        System.out.print("Codigo de la region: ");
        char tCodReg = sc.next().charAt(0);

        boolean tournamentAlreadyExists = true;
        do {
            tournamentAlreadyExists = serviceTournament.tournamentAlreadyExists(new Ent_Tournament(tName, tCodReg));

            if (tournamentAlreadyExists) {
                System.out.println("Nombre-Region (" + tName + " | " + tCodReg + ") utilziados ya existen: ");
                System.out.print("Nombre del torneo: ");
                tName = sc.next();

                System.out.print("Codigo de la region: ");
                tCodReg = sc.next().charAt(0);
            }
        } while (tournamentAlreadyExists);

        System.out.print("Puntos Max. para victoria: ");
        float tVictoryPoints = sc.nextFloat();

        Tournament t = applicationContext.getBean(Tournament.class, tName, tCodReg, tVictoryPoints);
        while (t.getAdminTournament() == null) {
            t.generateTournamentAdmin();
        }

        serviceTournament.createTournament(new Ent_Tournament(tName, tCodReg, tVictoryPoints), t.getAdminTournament());

        TournamentList.getInstance().addTournament(t);
        adminTMenu.openMenu(t.getAdminTournament());

        LogManagement.getInstance().writeLog("Tournament " + tName + " has been created");
    }
}
