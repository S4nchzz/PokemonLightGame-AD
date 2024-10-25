package org.lightPoke.menus;

import org.lightPoke.log.LogManagement;
import org.lightPoke.tournament.TournametList;
import org.lightPoke.users.TRUser;

import java.util.Scanner;

public class TrainerMenu {
    private final LogManagement log;
    public TrainerMenu(final TRUser trainer) {
        log = LogManagement.getInstance();

        if (trainer != null) {
            log.writeLog("Trainer has info (he has registered and autoLogged)");

            // Show tournaments
            Scanner sc = new Scanner(System.in);
            TournametList tournametList = TournametList.getInstance();

            if (tournametList.size() > 0) {
                System.out.println("------ Tu Primer Torneo ------");
                System.out.println(tournametList.listTournaments());
                System.out.print("?: ");

                int tChoice;
                while ((tChoice = sc.nextInt()) < 1 || tChoice > tournametList.size()) {
                    System.out.println("Torneo no encontrado, elija un torneo entre 1 y " + tournametList.size());
                    System.out.println(tournametList.listTournaments());
                }
                tournametList.addTrainer(trainer, tChoice);
            }

            System.out.println(tournametList);

            System.out.println("------ Trainer menu ------");
            System.out.println("1. Exportar carnet");
            System.out.println("2. Logout");

            int choice;
            while ((choice = sc.nextInt()) != 1 && choice != 2) {
                System.out.println("------ Trainer menu ------");
                System.out.println("1. Exportar carnet");
                System.out.println("2. Logout");
            }
        } else {
            log.writeLog("Trainer has no info (login before registered)");
        }
    }
}
