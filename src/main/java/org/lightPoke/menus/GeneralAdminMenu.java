package org.lightPoke.menus;

import org.lightPoke.Game;
import org.lightPoke.auth.Register;
import org.lightPoke.db.entity.Ent_Tournament;
import org.lightPoke.db.services.Svice_Tournament;
import org.lightPoke.log.LogManagement;
import org.lightPoke.tournament.Tournament;
import org.lightPoke.tournament.TournamentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

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
    public GeneralAdminMenu() {

    }

    public void openMenu() {
        System.out.println("------ General Admin menu ------");
        System.out.println("1. Registrar nuevo torneo");
        System.out.println("2. Logout \n");
        System.out.print("Please select an option: ");

        Scanner sc = new Scanner(System.in);
        int choice;
        while((choice = sc.nextInt()) != 1 && choice != 2) {
            System.out.println("------ General Admin menu ------");
            System.out.println("1. Registrar nuevo torneo");
            System.out.println("2. Logout \n");
            System.out.print("Please select an option: ");
        }

        switch (choice) {
            case 1 -> {
                createTournament();
            }

            case 2 -> {
                mainMenu.openMainMenu();
            }
        }
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

        boolean correctName = true;
        do {
            correctName = true;
            if (!serviceTournament.tournamentAlreadyExists(new Ent_Tournament(tName, tCodReg))) {
                correctName = false;
            }

            if (correctName) {
                System.out.println("Nombre-Region (" + tName + " | " + tCodReg + ") utilziados ya existen: ");
                System.out.print("Nombre del torneo: ");
                tName = sc.next();

                System.out.print("Codigo de la region: ");
                tCodReg = sc.next().charAt(0);
            }
        } while (correctName);

        System.out.print("Puntos Max. para victoria: ");
        float tVictoryPoints = sc.nextFloat();


        Tournament t = applicationContext.getBean(Tournament.class, tName, tCodReg, tVictoryPoints);
        while (t.getAdminTournament() == null) {
            System.out.println("El usuario ya existe.");
            t.generateTournamentAdmin();
        }

        serviceTournament.createTournament(new Ent_Tournament(tName, tCodReg, tVictoryPoints), t.getAdminTournament());

        TournamentList.getInstance().addTournament(t);
        adminTMenu.openMenu(t.getAdminTournament());

        LogManagement.getInstance().writeLog("Tournament " + tName + " has been created");
    }
}
