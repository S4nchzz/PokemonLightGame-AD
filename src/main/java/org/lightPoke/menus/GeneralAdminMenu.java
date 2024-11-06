package org.lightPoke.menus;

import org.lightPoke.Game;
import org.lightPoke.log.LogManagement;
import org.lightPoke.tournament.Tournament;
import org.lightPoke.tournament.TournamentList;
import java.util.Scanner;

/**
 * Clase que solo sera accesible cuando un usuario de tipo
 * Administrador general (AGUser) inicia sesion permitiendo
 * poder crear un torneo.
 *
 * @author Iyan Sanchez da Costa
 */
public class GeneralAdminMenu {
    public GeneralAdminMenu() {
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
                Game.main(null);
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
            for (Tournament t : TournamentList.getInstance().getTournamentList()) {
                if (t.getNombre().equals(tName) && t.getCodRegion() == tCodReg) {
                    correctName = false;
                }
            }

            if (!correctName) {
                System.out.println("Nombre-Region (" + tName + " | " + tCodReg + ") utilziados ya existen: ");
                System.out.print("Nombre del torneo: ");
                tName = sc.next();

                System.out.print("Codigo de la region: ");
                tCodReg = sc.next().charAt(0);
            }
        } while (!correctName);



        System.out.print("Puntos Max. para victoria: ");
        float tVictoryPoints = sc.nextFloat();

        Tournament t = new Tournament(tName, tCodReg, tVictoryPoints);
        while (t.getAdminTournament() == null) {
            System.out.println("El usuario ya existe.");
            t = new Tournament(tName, tCodReg, tVictoryPoints);
        }
        TournamentList.getInstance().addTournament(t);
        new AdminTournamentMenu();

        LogManagement.getInstance().writeLog("Tournament " + tName + " has been created");
    }
}
