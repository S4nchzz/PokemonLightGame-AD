package org.lightPoke.auth;

import org.lightPoke.auth.nacionality.Pais;
import org.lightPoke.auth.nacionality.PaisesLoader;
import org.lightPoke.log.LogManagement;
import org.lightPoke.users.TRUser;

import java.io.*;
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

            TRUser user = requestInfo(username, password);
            writer = new BufferedWriter(new FileWriter(credentialsFile, true));
            writer.write(username);
            writer.newLine();
            writer.write(password);
            writer.newLine();
            writer.write("T");
            writer.newLine();
            writer.close();

            log.writeLog("User " + username + " has been registrated");
            return user;
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

        System.out.print("Elija una de las nacionalidades que hay: ");
        System.out.println(nacionalityList());

        System.out.print("?: ");
        String nacionality = sc.next();

        while (!checkIfNacionalityExist(nacionality)){
            System.out.println("La nacionaldad elegida no forma parte de la lista, intentelo de nuevo");
            System.out.println(nacionalityList());
            System.out.print("?: ");

            nacionality = sc.next();
        }

        TRUser trainer = new TRUser(username, password, 2);
        trainer.generateInfo(newId, name, nacionality.toUpperCase());
        newId++;

        return trainer;
    }

    private String nacionalityList() {
        StringBuilder sb = new StringBuilder();
        for (Pais c: PaisesLoader.getCountriesList()) {
            sb.append(c.getId() + " | ");
        }

        return sb.toString();
    }

    private boolean checkIfNacionalityExist(final String userNacionality) {
        for (Pais c : PaisesLoader.getCountriesList()) {
            if (userNacionality.equalsIgnoreCase(c.getId()) ||userNacionality.equalsIgnoreCase(c.getNombre())) {
                return true;
            }
        }

        return false;
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