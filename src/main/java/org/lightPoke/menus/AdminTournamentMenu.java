package org.lightPoke.menus;

import org.lightPoke.Game;

import java.util.Scanner;

/**
 * Clase que solo sera accesible cuando un usuario de tipo
 * Administrador de torneo (ATUser) inicia sesion
 *
 * @author Iyan Sanchez da Costa
 */
public class AdminTournamentMenu {
    public AdminTournamentMenu() {
        System.out.println("------ Admin Tournament menu ------");
        System.out.println("1. Exportar datos de los torneos");
        System.out.println("2. Inscribirse");
        System.out.println("3. Pelear");
        System.out.println("4. Logout");

        Scanner sc = new Scanner(System.in);
        int choice;
        while ((choice = sc.nextInt()) != 1 && choice != 2 & choice != 3 && choice != 4) {
            System.out.println("Opcion no valida, intentelo de nuevo\n");

            System.out.println("------ Admin Tournament menu ------");
            System.out.println("1. Exportar datos de los torneos");
            System.out.println("2. Inscribirse");
            System.out.println("3. Pelear");
            System.out.println("4. Logout");
        }

        switch (choice) {
            case 1 -> {}
            case 2 -> {}
            case 3 -> {}
            case 4 -> {
                Game.main(null);
            }
        }
    }
}
