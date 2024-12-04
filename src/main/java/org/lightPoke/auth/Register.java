package org.lightPoke.auth;

import org.lightPoke.auth.nacionality.Pais;
import org.lightPoke.auth.nacionality.PaisesLoader;
import org.lightPoke.db.dto.TrainerDTO;
import org.lightPoke.db.services.At_InTournamentService;
import org.lightPoke.db.services.TrainerService;
import org.lightPoke.log.LogManagement;
import org.lightPoke.users.ATUser;
import org.lightPoke.users.TRUser;
import org.lightPoke.users.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Clase singelton que representa toda la interaccion
 * del usuario invitado con el sistema Register
 * @author Iyan Sanchez da Costa
 */
public class Register {
    private static Register instance;

    private File credentialsFile;
    private final LogManagement log;

    private Register() {
        credentialsFile = new File("./src/main/resources/users/credenciales.txt");
        log = LogManagement.getInstance();
    }

    /**
     * Metodo que retorna una unica instancia de tipo Register.class
     * @return Nueva instancia si no existia previamente
     */
    public static Register getInstance() {
        return instance == null ? instance = new Register() : instance;
    }

    /**
     * Metodo que dados 3 parametros intentara registrar al nuevo usuario
     * en el sistema escribiendo su usuario, contraseña, tipo e ID en el
     * archivo credenciales.txt
     *
     * Un usuario de credenciales.txt se veria de esta forma
     * {usuario, contraseña, tipo, ID | iyan, iyn, AG, 10}
     *
     * Delsglose del codigo:
     * Si el usuario no existe y el tipo coincide con "AT, AG, TR" con los
     * tipos de usuario que existen creara dependiendo del tipo un usuario
     * u otro, si el usuario es de tipo "AT" (administrador de torneo) creara
     * simplemente un usuario de ese tipo, en cambio si el tipo es "TR" (entrenador)
     * se hara una llamada a un metodo que le pedira informacion adicional al
     * entrenador como su nombre y nacionalidad, cuando este proporcione la
     * informacion correcamente devolvera un objeto de tipo TRUser y se escribira
     * en el archivo credenciales.txt los datos del usuario con la estructura mencionada
     *
     * @param username Usuario a registrarse
     * @param password Contraseña del usuario a registrarse
     * @param type Tipo de usuario proporcionado por el sistema (TR || AT)
     * @return Objeto de tipo User que engloba usuarios de tipo administrador de torneo y entrenadores
     */
    public User register(final String username, final String password, final String type) {
        At_InTournamentService atInTournamentService = At_InTournamentService.getInstance();
        BufferedWriter writer = null;
        try {
            if (userExistInFile(username) || atInTournamentService.userExistInDatabase(username)) {
                log.writeLog("User " + username + " already exist in credentialsFile or database");
                return null;
            }

            if (!type.equalsIgnoreCase("AT") && !type.equalsIgnoreCase("AG") && !type.equalsIgnoreCase("TR")) {
                log.writeLog("Type on registration doesn't match the required types -- Provided: " + type);
                return null;
            }

            User user = null;
            switch(type.toUpperCase()) {
                case "TR" -> {
                    user = requestInfo(username, password);

                    TrainerService trainerService = TrainerService.getInstance();
                    trainerService.createTrainer(user);

                    System.out.println((TRUser)user);
                }
                case "AT" -> {user = new ATUser(username, password, 2);}
            }

            writer = new BufferedWriter(new FileWriter(credentialsFile, true));

            writer.write(username + " " + password + " " + type + " " + getNextId());

            writer.newLine();
            writer.close();

            log.writeLog("User " + username + " has been registrated");
            return user;
        } catch (IOException e) {
            log.writeLog("Error while reading credentialsFile -- err register");
        }

        return null;
    }

    /**
     * Metodo que obtiene de la lista usuarios de credenciales.txt el
     * ultimo ID y le suma uno
     *
     * @return Ultimo ID + 1
     */
    public int getNextId() {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(credentialsFile));
            Stream<String> lines = reader.lines();

            String [] lineValues = new String[3];
            int lineCount = 0;
            List<String> lineList = lines.toList();

            for (String line : lineList) {
                if (lineCount + 1 == lineList.size()) {
                    lineValues = line.split(" ");
                }

                lineCount++;
            }

            if (lineValues.length >= 3) {
                return Integer.parseInt(lineValues[3]) + 1;
            }
        } catch (IOException e) {

        }

        return -1;
    }

    /**
     * Metodo que le pide a un usuario a la hora de registrarse un nombre
     * y un pais de una lista de paises proporcionados por un objeto de tipo
     * Paises.class el cual contiene una List<Pais> y cada entrada de la lista
     * contiene el nombre del pais y la abreviatura, finalmente se creara un
     * objeto de tipo TRUser y se le hara una llamada al metodo generateInfo()
     * con los nuevos valores añadidos.
     *
     * @param username Usuario a registrarse
     * @param password Contraseña del usuario a registrarse
     * @return Usuario de tipo TRUser(entrenador) con los datos completos
     */
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

        TRUser trainer = new TRUser(username, password, 1);
        trainer.generateInfo(getNextId(), name, nacionality.toUpperCase());

        return trainer;
    }

    /**
     * Metodo que devuelve en forma de String la lista de los paises
     * usndo su abreviatura gracias a la clase PaisesLoader.class
     * @return
     */
    private String nacionalityList() {
        StringBuilder sb = new StringBuilder();
        for (Pais c: PaisesLoader.getCountriesList()) {
            sb.append(c.getId() + " | ");
        }

        return sb.toString();
    }

    /**
     * Método que comprueba si la nacionalidad proporcionada existe, ya sea por
     * abreviatura o nombre del país, usando la lista de la clase PaisesLoader.
     *
     * @param userNacionality Nacionalidad proporcionada por el usuario
     * @return true si se ha encontrado una nacionalidad | false si no exise dicha nacionalidad
     */
    private boolean checkIfNacionalityExist(final String userNacionality) {
        for (Pais c : PaisesLoader.getCountriesList()) {
            if (userNacionality.equalsIgnoreCase(c.getId()) ||userNacionality.equalsIgnoreCase(c.getNombre())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Metodo que comprueba leyendo el archivo credenciales.txt si
     * el usuario ya existia.
     *
     * Un usuario de credenciales.txt se veria de esta forma
     * {usuario, contraseña, tipo, ID | iyan, iyn, AG, 10}
     *
     * Deslogse del codigo:
     * Mientras la linea que se eta leyendo no este vacia usando
     * .split() se obtendra un array con cada palabra en esa linea
     * y si el username que solicitan para el registro coincide con
     * la primera posicion de la lista de palabras de la linea
     * significara que el usuaro ya existe.
     *
     * @param username Usuario a verificar si existe o no
     * @return true si el usuario ya existe | false si el usuario no existe
     */
    private boolean userExistInFile(final String username) {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(credentialsFile));

            String line;
            while ((line = reader.readLine()) != null) {
                String [] lineValues = line.split(" ");
                if (username.equals(lineValues[0])) {
                    return true;
                }
            }

            reader.close();
        } catch (IOException e) {

        }

        return false;
    }
}