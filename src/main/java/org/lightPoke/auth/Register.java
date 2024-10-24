package org.lightPoke.auth;

import org.lightPoke.log.LogManagement;
import org.lightPoke.trainerLicense.Carnet;
import org.lightPoke.users.TRUser;
import org.lightPoke.users.User;

import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

public class Register {
    private static Register instance;

    private File credentialsFile;
    private final LogManagement log;

    private int newId;

    private Register() {
        credentialsFile = new File("./src/main/resources/users/credenciales.txt");
        log = LogManagement.getInstance();
        this.newId = 0;
    }

    public static Register getInstance() {
        return instance == null ? instance = new Register() : instance;
    }

    public TRUser register(final String username, final String password) {
        BufferedWriter writer = null;
        try {
            if (userExist(username)) {
                log.writeLog("User " + username + " already exist");
                return null;
            }

            writer = new BufferedWriter(new FileWriter(credentialsFile, true));
            writer.write(username);
            writer.newLine();
            writer.write(password);
            writer.newLine();
            writer.write("T");
            writer.newLine();
            writer.close();

            log.writeLog("User " + username + " has been registrated");
            return requestInfo(username, password);
        } catch (IOException e) {
            log.writeLog("Error while reading credentialsFile -- err register");
        }

        return null;
    }

    private TRUser requestInfo(final String username, final String password) {
        Scanner sc = new Scanner(System.in);

        System.out.println("------ Trainer info ------");
        System.out.println("Nombre: ");
        final String name = sc.next();

        System.out.println("Elija una de las nacionalidades que hay: ");
        nacionalityList();
        final String nacionality = sc.next();

        TRUser trainer = new TRUser(username, password, 2);
        trainer.generateInfo(newId, name, nacionality);
        newId++;

        return trainer;
    }

    private void nacionalityList() {
    }

    private boolean userExist(final String username) {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(credentialsFile));
            String x;
            int lineCount = 1;
            while ((x = reader.readLine()) != null) {
                if (x.equals(username) && lineCount % 2 != 0) {
                    return true;
                }

                lineCount++;
            }

            reader.close();
        } catch (IOException e) {

        }

        return false;
    }
}