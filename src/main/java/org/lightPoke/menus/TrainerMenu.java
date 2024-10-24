package org.lightPoke.menus;

import org.lightPoke.log.LogManagement;
import org.lightPoke.users.TRUser;

import java.util.Scanner;

public class TrainerMenu {
    private final LogManagement log;
    public TrainerMenu(final TRUser trainer) {
        log = LogManagement.getInstance();

        if (trainer != null) {
            log.writeLog("Trainer menu opened with trainer info");
        } else {
            log.writeLog("Trainer menu opened without trainer info");
        }

        System.out.println("------ Trainer menu ------");
        System.out.println("1. Exportar carnet");
        System.out.println("2. Logout");
    }
}
