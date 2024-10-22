package org.lightPoke.auth;

import org.lightPoke.log.LogManagement;

import java.io.*;

public class Login {
    private static Login instance;
    private File credentialsFile;
    private final LogManagement log;

    private Login() {
        credentialsFile = new File("./src/main/resources/users/credenciales.txt");
        log = LogManagement.getInstance();
    }

    public static Login getInstance() {
        return instance == null ? instance = new Login() : instance;
    }

    public boolean login(final String username, final String password) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(credentialsFile));
            String x;
            while ((x = reader.readLine()) != null) {
                if (x.equals(username) && (reader.readLine()).equals(password)) {
                    log.writeLog("User " + username + " logged succesfully");
                    return true;
                }
            }

            log.writeLog("User " + username + " not found");
        } catch (IOException e) {
            log.writeLog("Failed to read \"credenciales.txt\", make sure this file exist");
        }
        return false;
    }
}
