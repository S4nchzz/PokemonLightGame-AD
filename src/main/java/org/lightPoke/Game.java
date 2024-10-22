package org.lightPoke;

import com.sun.security.jgss.GSSUtil;
import org.lightPoke.auth.Login;
import org.lightPoke.auth.Register;
import org.lightPoke.auth.UserData;
import org.lightPoke.log.LogManagement;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Game {
    private static LogManagement log = LogManagement.getInstance();
    public static void main(String[] args) {
        Login log = Login.getInstance();
        Register reg = Register.getInstance();

        mainMenu();
    }

    private static void mainMenu() {
        System.out.println("------ Welcome to LightPoke ------");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
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
                if (register()) {
                    System.out.println("Registration successful!");
                } else {
                    System.out.println("Registration failed.");
                }
                break;
            case 2:
                login();
                break;
            case 3:
                System.out.println("Exiting...");
                return;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void guestMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("------ Guest menu ------");
        System.out.println("1. Ver torneos");
        System.out.println("2. Salir...");
    }

    private static void adminTournametMenu() {
        System.out.println("------ Admin Tournament menu ------");
        System.out.println("1. Exportar datos de los torneos");
        System.out.println("2. Inscribirse");
        System.out.println("3. Pelear");
    }

    private static void trainerMenu() {
        System.out.println("------ Trainer menu ------");
        System.out.println("1. Exportar carnet");
    }

    private static void generalAdminMenu() {
        System.out.println("------ General Admin menu ------");
        System.out.println("1. Registrar nuevo torneo");
    }

    private static boolean login () {
        Scanner sc = new Scanner(System.in);
        System.out.println("------ Registro ------");

        System.out.print("Usuario: ");
        final String username = sc.next();

        System.out.print("Password: ");
        final String password = sc.next();

        Login log = Login.getInstance();

        UserData data = log.login(username, password);
        if (data != null) {
            System.out.println("Login successful!");

            switch (data.getRole()) {
                case 1 -> {
                    System.out.println("Logged as Admin tournament");
                    adminTournametMenu();
                    break;
                }
                case 2 -> {
                    System.out.println("Logged as Trainer");
                    trainerMenu();
                    break;
                }
                case 3 -> {
                    System.out.println("Logged as General Admin");
                    generalAdminMenu();
                    break;
                }
                default -> {
                    break;
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
                    guestMenu();
                }
                case 'n' -> {return false;}
            }
        }

        return false;
    }

    private static boolean register() {
        Scanner sc = new Scanner(System.in);
        System.out.println("------ Registro ------");

        System.out.print("Usuario: ");
        final String username = sc.next();

        System.out.print("Password: ");
        final String password = sc.next();

        Register reg = Register.getInstance();
        return reg.register(username, password);
    }
}