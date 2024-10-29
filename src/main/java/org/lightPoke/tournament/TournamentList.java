package org.lightPoke.tournament;

import org.lightPoke.log.LogManagement;
import org.lightPoke.users.TRUser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TournamentList {
    private static TournamentList instance;
    private List<Tournament> tournamentList;
    private final LogManagement log;

    private TournamentList() {
        this.tournamentList = new ArrayList<>();
        log = LogManagement.getInstance();
        retriveTournamentsFromDat();
    }

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

    public static TournamentList getInstance() {
        return instance == null ? instance = new TournamentList(): instance;
    }

    public void addTournament(final Tournament tournament, final boolean append) {
        this.tournamentList.add(tournament);
        reWriteTournamentIntoDat();
        log.writeLog("Tournament " + tournament.getNombre() + " added.");
    }

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

    public void addTrainer(final TRUser trainer, final int positionIndex) {
        this.tournamentList.get(positionIndex - 1).addTrainer(trainer);
        reWriteTournamentIntoDat();
    }

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

    public String listTournaments() {
        StringBuilder sb = new StringBuilder();

        int index = 1;
        for (Tournament t : this.tournamentList) {
            sb.append(index + ". Nombre: " + t.getNombre() + "\n");
            index++;
        }

        return sb.toString();
    }

    public int size() {
        return this.tournamentList.size();
    }

    public List<Tournament> getTournamentList() {
        return this.tournamentList;
    }

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