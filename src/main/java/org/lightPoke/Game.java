package org.lightPoke;

import org.lightPoke.menus.MainMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase Game que se encarga de gestionar toda la interaccion
 * de un usuario invitado
 *
 * @author Iyan Sanchez da Costa
 */

@SpringBootApplication
public class Game implements CommandLineRunner {
    @Autowired
    private MainMenu mainMenu;

    public static void main(String[] args) {
        SpringApplication.run(Game.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        mainMenu.openMainMenu();
    }
}