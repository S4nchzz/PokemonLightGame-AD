package org.lightPoke.menus;

import org.lightPoke.Game;
import org.lightPoke.db.dao.services.TournamentDAO_IMPLE;
import org.lightPoke.db.dto.CombatDTO;
import org.lightPoke.db.dto.TournamentDTO;
import org.lightPoke.db.services.TournamentService;
import org.lightPoke.log.LogManagement;
import org.lightPoke.tournament.Tournament;
import org.lightPoke.tournament.TournamentList;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;
import java.util.Scanner;

/**
 * Clase que solo sera accesible cuando un usuario de tipo
 * Administrador general (AGUser) inicia sesion permitiendo
 * poder crear un torneo.
 *
 * @author Iyan Sanchez da Costa
 */
public class GeneralAdminMenu {
    private final TournamentService tournamentService = TournamentService.getInstance();
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
            if (!tournamentService.isTournamentAvailable(new TournamentDTO(tName, tCodReg))) {
                correctName = false;
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

        tournamentService.createTournament(new TournamentDTO(tName, tCodReg, tVictoryPoints), t.getAdminTournament());

        TournamentList.getInstance().addTournament(t);
        new AdminTournamentMenu(t.getAdminTournament());

        LogManagement.getInstance().writeLog("Tournament " + tName + " has been created");
    }
}
