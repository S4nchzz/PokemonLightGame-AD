package org.lightPoke;

import org.lightPoke.auth.Login;
import org.lightPoke.auth.Register;
import org.lightPoke.log.LogManagement;
import org.lightPoke.menus.AdminTournamentMenu;
import org.lightPoke.menus.GeneralAdminMenu;
import org.lightPoke.menus.GuestMenu;
import org.lightPoke.menus.TrainerMenu;
import org.lightPoke.tournament.Tournament;
import org.lightPoke.tournament.TournametList;
import org.lightPoke.users.ATUser;
import org.lightPoke.users.TRUser;
import org.lightPoke.users.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private static LogManagement log = LogManagement.getInstance();
    public static void main(String[] args) {
        //TournametList t = TournametList.getInstance();
        //t.addTournament(new Tournament(new ATUser("hola", "hola", 2), 1, "showdown", 'A', 100.0f), true);
        //t.addTournament(new Tournament(new ATUser("hola", "hola", 2), 1, "showdown 2", 'A', 100.0f), true);

        mainMenu();
    }

    private static void mainMenu() {
        System.out.println("------ Welcome to LightPoke ------");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Unirse como invitado");
        System.out.println("4. Exit \n");
        System.out.print("Please select an option: ");

        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        while (choice != 1 && choice != 2 && choice != 3 && choice != 4) {
            System.out.println("------ Welcome to LightPoke ------");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Unirse como invitado");
            System.out.println("4. Exit \n");
            System.out.print("Please select an option: ");
            choice = sc.nextInt();

        }

        switch (choice) {
            case 1:
                TRUser user = register();
                if (user != null) {
                    new TrainerMenu(user); // Automatic login after registration
                }
                break;
            case 2:
                login();
                break;
            case 3:
                GuestMenu guestMenu = new GuestMenu();
                return;
            case 4:
                System.out.println("Exiting...");
                System.exit(1);
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static boolean login () {
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
                    new TrainerMenu(null); // Values not initialized
                }

                case 2 -> {
                    System.out.println("Logged as Admin tournament");
                    new AdminTournamentMenu();
                }

                case 3 -> {
                    System.out.println("Logged as General Admin");
                    new GeneralAdminMenu();
                }
            }
        } else {
            System.out.println("No se ha encontrado el usuario. ¿Entrar como invitado? (y/n)");
            char guestChoice;
            while ((guestChoice = sc.next().charAt(0)) != 'y' && guestChoice != 'n') {
                System.out.println("Opcion invalida | No se ha encontrado el usuario. ¿Entrar como invitado? (y/n)");
            }

            switch (guestChoice) {
                case 'y' -> {
                    new GuestMenu();
                }
                case 'n' -> {return false;}
            }
        }

        return false;
    }

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
        } while ((user = reg.register(username, password)) == null);

        return user;
    }
}