package org.lightPoke.auth;

import org.lightPoke.db.db4o.entities.UserEnt_db4o;
import org.lightPoke.db.db4o.services.UserService_db4o;
import org.lightPoke.log.LogManagement;
import org.lightPoke.users.AGUser;
import org.lightPoke.users.ATUser;
import org.lightPoke.users.TRUser;
import org.lightPoke.users.User;

import java.io.*;

/**
 * Clase singelton que representa toda la interaccion
 * del usuario invitado con el sistema de Login
 * @author Iyan Sanchez da Costa
 */
public class Login {
    private static Login instance;
    private final LogManagement log;

    private UserService_db4o userServiceDb4o;

    private Login() {
        log = LogManagement.getInstance();
        this.userServiceDb4o = new UserService_db4o();
    }

    /**
     * Metodo que retorna una unica instancia de tipo Login.class
     * @return Nueva instancia si no existia previamente
     */
    public static Login getInstance() {
        return instance == null ? instance = new Login() : instance;
    }

    /**
     * Metodo que dado un usuario y contraseña leera en el archivo
     * "credenciales.txt" todos los usuarios hasta encontrar un
     * usuario que coincida con uno de los encontrados.
     *
     * Un usuario de credenciales.txt se veria de esta forma
     * {usuario, contraseña, tipo, ID | iyan, iyn, AG, 10}
     *
     * Desglose del codigo:
     * Mientras credenciales.txt no tenga lineas vacias se separara
     * en un array de tipo String cada una de las palabras y si en
     * la primera posicion (usuario) y la segunda posicion(contraseña)
     * coinciden con los parametros que se le pasa al metodo retornara
     * un objeto de tipo User, este objeto de tipo user podra ser de 3
     * tipos, dependiendo del tercer valor del array se creara uno u otro     *
     * @param username Usuario solicitado
     * @param password Contraseña solicitada
     * @return Objecto de tipo User con los datos del usuario + su rol
     */
    public User login(final String username, final String password) {
        final UserEnt_db4o user = userServiceDb4o.findByCredentials(username, password);
        if (user != null) {
            log.writeLog("User " + username + " logged succesfully");
            switch (user.getType().toUpperCase()) {
                case "TR" -> {return new TRUser(username, password, 1);} // Entrenador
                case "AT" -> {return new ATUser(username, password, 2);} // Administrador de torneos
                case "AG" -> {return new AGUser(username, password, 3);} // Administrador general
            }
        }

        log.writeLog("User " + username + " not found");
        return null;
    }
}
