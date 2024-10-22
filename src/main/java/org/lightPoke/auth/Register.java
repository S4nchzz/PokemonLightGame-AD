package org.lightPoke.auth;

import org.lightPoke.log.LogManagement;

import java.io.*;

public class Register {
    private static Register instance;
    private File credentialsFile;
    private final LogManagement log;

    private Register() {
        credentialsFile = new File("./src/main/resources/users/credenciales.txt");
        log = LogManagement.getInstance();
    }

    public static Register getInstance() {
        return instance == null ? instance = new Register() : instance;
    }

    public boolean register(final String username, final String password) {
        BufferedWriter writer = null;
        try {
            if (userExist(username)) {
                log.writeLog("User " + username + " already exist");
                return false;
            }

            writer = new BufferedWriter(new FileWriter(credentialsFile, true));

            writer.write(username);
            writer.newLine();
            writer.write(password);
            writer.newLine();
            writer.write("trainer");
            writer.newLine();
            writer.close();

            log.writeLog("User " + username + " has been registrated");
            return true;
        } catch (IOException e) {
            log.writeLog("Error while reading credentialsFile -- err register");
        }

        return false;
    }

    private boolean userExist(final String username) {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(credentialsFile));
            String x;
            while ((x = reader.readLine()) != null) {
                if (x.equals(username)) {
                    return true;
                }
            }

            reader.close();
        } catch (IOException e) {

        }

        return false;
    }
}