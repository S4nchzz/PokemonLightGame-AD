package org.lightPoke.tournament;

import org.lightPoke.log.LogManagement;
import org.lightPoke.users.TRUser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase singelton que gestiona todos los torneos en una
 * lista y gracias a un getter se accede a esta
 *
 * @author Iyan Sanchez da Costa
 */
public class TournamentList {
    private static TournamentList instance;
    private List<Tournament> tournamentList;
    private final LogManagement log;

    private TournamentList() {
        this.tournamentList = new ArrayList<>();
        log = LogManagement.getInstance();
        retriveTournamentsFromDat();
    }

    /**
     * Este metodo sera llamado en la creacion de la instancia singelton
     * para asi poder rellenar la lista de torneos usando ObjectInputStream
     * el cual leera del archivo tournaments.dat una lista de torneos
     * serializada
     */
    private void retriveTournamentsFromDat() {
        ObjectInputStream in = null;
        File file = new File("./src/main/resources/src/tournaments.dat");
        if (!file.exists()) {
            file = new File("./src/main/resources/src", "tournaments.dat");
            try {file.createNewFile();} catch (IOException e) {}
        }

        try {
            in = new ObjectInputStream(new FileInputStream(file));

            while (true) {
                this.tournamentList.add((Tournament)in.readObject());
            }

        } catch (IOException | ClassNotFoundException e) {
            if (in != null) {
                try {in.close();} catch (IOException ex) {}
            } else {
                log.writeLog("tournaments.dat has no data");
            }
        }
    }

    /**
     * Metodo que devuelve una instancia de la misma clase
     * si no existia de ante mano
     * @return Instancia de TournamentList.class
     */
    public static TournamentList getInstance() {
        return instance == null ? instance = new TournamentList(): instance;
    }

    /**
     * Metodo que proporcionandole un torneo, este lo añadira a la lista
     * y una vez este añadido se llamara a un metodo que sobreescribira
     * tournaments.dat
     * @param tournament Torneo a añadir
     */
    public void addTournament(final Tournament tournament) {
        this.tournamentList.add(tournament);
        reWriteTournamentIntoDat();
        log.writeLog("Tournament " + tournament.getNombre() + " added.");
    }

    /**
     * Metodo que proporcionandole un torneo, este lo eliminara a la lista
     * y una vez este eliminado se llamara a un metodo que sobreescribira
     * tournaments.dat
     * @param tournament Torneo a eliminar
     */
    public void removeTournament(final Tournament tournament) {
        int indexToDelete = 0;
        for (Tournament t : tournamentList) {
            if (t.getId() == tournament.getId()) {
                break;
            }

            indexToDelete++;
        }

        this.tournamentList.remove(indexToDelete);
    }

    /**
     * Metodo que dependiendo de una posicion en la lista de torneos
     * se le añadira un nuevo entrenador y se sobreescribira este
     * @param trainer Entrenador a añadir
     * @param positionIndex Posicion del torneo
     */
    public void addTrainer(final TRUser trainer, final int positionIndex) {
        this.tournamentList.get(positionIndex - 1).addTrainer(trainer);
        reWriteTournamentIntoDat();
    }

    /**
     * Metodo que sera llamado cuando la lista se modifique, esto hara
     * que se puedan escribir varios objetos simultaneamente usando
     * ObjectOutputStream
     */
    private void reWriteTournamentIntoDat() {
        try {
            // Clear the dat file
            DataOutputStream dat = new DataOutputStream(new FileOutputStream(new File("./src/main/resources/src/tournaments.dat"), false));
            dat.close();

            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File("./src/main/resources/src/tournaments.dat"), true));
            for (int i = 0; i < tournamentList.size(); i++) {
                out.writeObject(tournamentList.get(i));
            }
        } catch (IOException e) {
            log.writeLog("Tournaments.dat not found or error on reading");
        }
    }

    /**
     * Metodo que devuelve la informacion de un torneo
     * @return Concatenacion de informacion de torneos
     */
    public String listTournaments() {
        StringBuilder sb = new StringBuilder();

        int index = 1;
        for (Tournament t : this.tournamentList) {
            sb.append(index + ". Nombre: " + t.getNombre() + "\n");
            index++;
        }

        return sb.toString();
    }

    /**
     * Tamaño de la lista de torneos
     * @return Longitud de la lista
     */
    public int size() {
        return this.tournamentList.size();
    }

    /**
     * Metodo que devuelve la lista de torneos
     * @return Lista de torneos
     */
    public List<Tournament> getTournamentList() {
        return this.tournamentList;
    }

    /**
     * Metodo que devuelve toda la informacion de los torneos
     * @return Informacion de los torneos
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Tournament t : this.tournamentList) {
            sb.append("\n\n");
            sb.append("\nID: " + t.getId());
            sb.append("\nAdministrador del torneo: " + t.getAdminTournament().getUsername());
            sb.append("\nNombre: " + t.getNombre());
            sb.append("\nCODReg: " + t.getCodRegion());
            sb.append("\nEntrenadores: " + t.getTrainers().size());
            sb.append("\nPuntos victoria: " + t.getPuntosVictoria());
        }

        return sb.toString();
    }
}