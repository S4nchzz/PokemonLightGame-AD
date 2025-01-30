package org.lightPoke.tournament;

import org.lightPoke.auth.Register;
import org.lightPoke.users.ATUser;
import org.lightPoke.users.TRUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase que gestiona los torneos con su informacion propia
 *
 * @author Iyan Sanchez da Costa
 */

@Component
public class Tournament implements Serializable {
    @Autowired
    private Register register;

    private int id;
    private ArrayList<TRUser> trainers;
    private String nombre;
    private ATUser adminTournament;
    private char codRegion;
    private float puntosVictoria;

    public Tournament(String nombre, char codRegion, float puntosVictoria) {
        this.nombre = nombre;
        this.codRegion = codRegion;
        this.puntosVictoria = puntosVictoria;

        trainers = new ArrayList<>();
    }

    /**
     * Metodo que es llamado por el Administrador General (AGUser)
     * el cual creara un usuario administrador de torneo (ATUser)
     * y este sera responsable de gestionar este torneo
     * @return Usuario administrador de torneos creado por el usuario Admin general
     */
    public void generateTournamentAdmin() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n------Registro de administrador de torneo------");
        System.out.print("Usuario: ");

        String username = sc.next();
        System.out.print("Contraseña: ");
        String password = sc.next();


        System.out.print("Los datos introducidos son correctos? (y/n): ");
        char choice;
        while ((choice = sc.next().charAt(0)) != 'y' && choice != 'n') {
            System.out.println("Opcion invalida (y | n)");
        }

        while (choice == 'n') {
                System.out.println("\n------Registro de administrador de torneo------");
                System.out.print("Usuario: ");

                username = sc.next();
                System.out.print("Contraseña: ");
                password = sc.next();

                System.out.print("Los datos introducidos son correctos? (y/n): ");
                while ((choice = sc.next().charAt(0)) != 'y' && choice != 'n') {
                    System.out.println("Opcion invalida (y | n)");
                }
            }

        this.adminTournament = (ATUser) register.register(username, password, "AT");
    }

    public int getId() {
        this.id = register.getNextId();
        return id;
    }

    public ArrayList<TRUser> getTrainers() {
        return trainers;
    }

    public void addTrainer(final TRUser trainer) {
        this.trainers.add(trainer);
    }

    public ATUser getAdminTournament() {
        return this.adminTournament;
    }

    public String getNombre() {
        return nombre;
    }

    public char getCodRegion() {
        return codRegion;
    }

    public float getPuntosVictoria() {
        return puntosVictoria;
    }
}
