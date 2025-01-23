package org.lightPoke.menus;

import org.lightPoke.Game;
import org.lightPoke.db.entity.Ent_At_InTournament;
import org.lightPoke.db.entity.Ent_Combat;
import org.lightPoke.db.entity.Ent_JoinTournamentRequest;
import org.lightPoke.db.entity.Ent_Tournament;
import org.lightPoke.db.services.Svice_Admin_InTournament;
import org.lightPoke.db.services.Svice_Combat;
import org.lightPoke.db.services.Svice_JoinTournamentRequest;
import org.lightPoke.db.services.Svice_Tournament;
import org.lightPoke.users.ATUser;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;
import java.util.Scanner;

/**
 * Clase que solo sera accesible cuando un usuario de tipo
 * Administrador de torneo (ATUser) inicia sesion
 *
 * @author Iyan Sanchez da Costa
 */
public class AdminTournamentMenu {
    private final ATUser tournamentAdmin;
    public AdminTournamentMenu(final ATUser tournamentAdmin) {
        Scanner sc = new Scanner(System.in);
        this.tournamentAdmin = tournamentAdmin;

        Ent_Tournament tournament = getTournament();
        int choice;
        boolean correctChoice = true;
        boolean keepLooping = true;

        do {
            if (!correctChoice) {
                System.out.println("Opcion no valida, intentelo de nuevo\n");
            }

            System.out.println("\n------ Admin Tournament menu ------");
            System.out.println("1. Mostrar datos del torneo");
            System.out.println("2. Exportar datos de los torneos");
            System.out.println("3. Ver solicitudes de union");
            System.out.println("4. Pelear");
            System.out.println("5. Logout");

            choice = sc.nextInt();
            if (choice < 0 || choice > 6) {
                correctChoice = false;
            } else {
                correctChoice = true;
            }

            switch (choice) {
                case 1 -> {
                    showTournamentData(tournament);
                }
                case 2 -> {
                    exportDataOfTournament(tournament);
                }
                case 3 -> {
                    viewTournamentRequest(tournament);
                }
                case 4 -> {}
                case 5 -> {
                    keepLooping = false; // La pila de ejecucion sigue ahi anque se entre a .main cuando la pilla acabe en este .main volvera a este loop
                    new Game();
                }
            }

        } while (keepLooping);

    }

    private void viewTournamentRequest(Ent_Tournament tournament) {
        Svice_JoinTournamentRequest joinTournamentRequestService = Svice_JoinTournamentRequest.getInstance();
        List<Ent_JoinTournamentRequest> requests = joinTournamentRequestService.getRequestsFromTournament(tournament);

        if (joinTournamentRequestService.tournamentRequestsIsEmpty(tournament.getId())) {
            System.out.println("Este torneo no tiene ninguna solicitud...");
            return;
        }

        Scanner sc = new Scanner(System.in);
        boolean choiced = false;
        int choice = -1;
        do {
            int index = 1;
            for (Ent_JoinTournamentRequest dto : requests) {
                System.out.println(index + " :" + dto.getTrainer().getName());
                index++;
            }
            System.out.print("?:");
            choice = sc.nextInt();

            if (choice > 0 || choice < requests.size()) {
                choiced = true;
            }
        } while (!choiced);

        boolean operationMaded = false;
        String operation = "";
        do {
            System.out.println("Accept / Decline");
            System.out.print("?: ");
            operation = sc.next();

            if (operation.equalsIgnoreCase("Accept") || operation.equalsIgnoreCase("Decline")) {
                operationMaded = true;
            } else {
                System.out.println("Opcion invalida \n");
            }
        } while (!operationMaded);

        Svice_JoinTournamentRequest requestService = Svice_JoinTournamentRequest.getInstance();
        if (operation.equalsIgnoreCase("Accept")) {
            Svice_Combat combatService = Svice_Combat.getInstance();
            combatService.addTrainerToTournamentCombat(requests.get(choice - 1).getTrainer().getId(), requests.get(choice - 1).getTournament().getId());
            requestService.deleteRequest(requests.get(choice - 1).getTrainer().getId(), tournament.getId());
        } else {
            requestService.deleteRequest(requests.get(choice - 1).getTrainer().getId(), tournament.getId());
        }
    }

    private Ent_Tournament getTournament() {
        Svice_Admin_InTournament atInTournamentService = Svice_Admin_InTournament.getInstance();
        Svice_Tournament tournamentService = Svice_Tournament.getInstance();

        Ent_At_InTournament atInTournamentDTO = atInTournamentService.getTournamentIdByAdminUsername(tournamentAdmin.getUsername());
        return tournamentService.getTournamentById(atInTournamentDTO.getTournamentDTO().getId());
    }

    private void showTournamentData(Ent_Tournament tournament) {
        System.out.println("----- Tournament -----");
        System.out.println("Nombre: " + tournament.getName());
        System.out.println("COD REGION: " + tournament.getRegion());
        System.out.println("Puntos de victoria: " + tournament.getVictoryPoints());

        if (tournament.getTWinner() == null) {
            System.out.println("Ganador: ????");
        } else {
            System.out.println("Ganador: " + tournament.getTWinner().getName());
        }
    }

    private void exportDataOfTournament(Ent_Tournament tournamentDTO) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, tournamentDTO.getName(), null);

            insertData(document.getDocumentElement(), document, tournamentDTO);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            File resultFile = new File("./src/main/resources/src/exported_tournaments", "tournament" + tournamentDTO.getId() + ".xml");
            Source source = new DOMSource(document);
            Result resultXML = new StreamResult(resultFile);

            transformer.transform(source, resultXML);

        } catch (ParserConfigurationException | TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertData(Element raiz, Document document, Ent_Tournament tournamentDTO) {
        Element eleTorneo = document.createElement("torneo");
        raiz.appendChild(eleTorneo);

        // Elemento ID
        Element eleId = document.createElement("id");
        Text idText = document.createTextNode(String.valueOf(tournamentDTO.getId()));
        eleId.appendChild(idText);

        eleTorneo.appendChild(eleId);

        // Elemento Name
        Element eleName = document.createElement("name");
        Text eleText = document.createTextNode(tournamentDTO.getName());
        eleName.appendChild(eleText);

        eleTorneo.appendChild(eleName);

        // Elemento Region
        Element eleCodRegion = document.createElement("cod_Region");
        Text eleRegion = document.createTextNode(String.valueOf(tournamentDTO.getRegion()));
        eleCodRegion.appendChild(eleRegion);

        eleTorneo.appendChild(eleCodRegion);

        // Elemento Victory points
        Element eleVPoints = document.createElement("victory_points");
        Text eleVPointsText = document.createTextNode(String.valueOf(tournamentDTO.getVictoryPoints()));
        eleVPoints.appendChild(eleVPointsText);

        eleTorneo.appendChild(eleVPoints);

        // Elemento T_WINNER
        Element eleWinner = document.createElement("winner");


        if (tournamentDTO.getTWinner() != null) {
            Text eleWinnerText = document.createTextNode(String.valueOf(tournamentDTO.getTWinner()));
            eleWinner.appendChild(eleWinnerText);
        }

        eleTorneo.appendChild(eleWinner);

        Element eleCombats = document.createElement("combates");
        Svice_Combat combatService = Svice_Combat.getInstance();

        for (Ent_Combat c : combatService.getCombatsByTournamentId(tournamentDTO.getId())) {
            Element combat = document.createElement("combate");

            Element date = document.createElement("date");
            Element trainer_1 = document.createElement("trainer_1");
            Element trainer_2 = document.createElement("trainer_2");
            Element winner = document.createElement("combatWinner");

            Text dateText = document.createTextNode(c.getDate());
            Text trainer_1Text = document.createTextNode(c.getTrainer_1().getName());
            Text trainer_2Text = document.createTextNode(c.getTrainer_2().getName());

            date.appendChild(dateText);
            trainer_1.appendChild(trainer_1Text);
            trainer_2.appendChild(trainer_2Text);
            if (c.getC_winner() != null) {
                Text winnerText = document.createTextNode(c.getC_winner().getName());
                winner.appendChild(winnerText);
            }

            combat.appendChild(date);
            combat.appendChild(trainer_1);
            combat.appendChild(trainer_2);
            combat.appendChild(winner);

            eleCombats.appendChild(combat);

            eleTorneo.appendChild(eleCombats);
        }
    }
}
