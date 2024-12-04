package org.lightPoke.menus;

import org.lightPoke.Game;
import org.lightPoke.db.dto.At_InTournamentDTO;
import org.lightPoke.db.dto.CombatDTO;
import org.lightPoke.db.dto.TournamentDTO;
import org.lightPoke.db.services.At_InTournamentService;
import org.lightPoke.db.services.TournamentService;
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
import java.io.IOException;
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
        this.tournamentAdmin = tournamentAdmin;

        System.out.println("------ Admin Tournament menu ------");
        System.out.println("1. Exportar torneo");
        System.out.println("2. Inscribirse");
        System.out.println("3. Pelear");
        System.out.println("4. Logout");

        Scanner sc = new Scanner(System.in);
        int choice;
        while ((choice = sc.nextInt()) < 0 || choice > 5) {
            System.out.println("Opcion no valida, intentelo de nuevo\n");

            System.out.println("------ Admin Tournament menu ------");
            System.out.println("1. Exportar datos de los torneos");
            System.out.println("2. Inscribirse");
            System.out.println("3. Pelear");
            System.out.println("4. Logout");
        }

        switch (choice) {
            case 1 -> {
                exportTournamentData();
            }
            case 2 -> {}
            case 3 -> {}
            case 4 -> {
                Game.main(null);
            }
        }
    }

    private void exportTournamentData() {
        System.out.println("----- Exportacion de datos torneo -----");

        At_InTournamentService atInTournamentService = At_InTournamentService.getInstance();
        TournamentService tournamentService = TournamentService.getInstance();

        At_InTournamentDTO atInTournamentDTO = atInTournamentService.getTournamentIdByAdminUsername(tournamentAdmin.getUsername());
        TournamentDTO tournament = tournamentService.getTournamentById(atInTournamentDTO.getTournamentDTO().getId());

        exportDataOfTournament(tournament);
    }

    private void exportDataOfTournament(TournamentDTO tournamentDTO) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, tournamentDTO.getName(), null);

            insertData(document.getDocumentElement(), document, tournamentDTO);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            File resultFile = new File("./src/main/resources/users/exported_tournaments", "tournament" + tournamentDTO.getId() + ".xml");
            Source source = new DOMSource(document);
            Result resultXML = new StreamResult(resultFile);

            transformer.transform(source, resultXML);

        } catch (ParserConfigurationException | TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertData(Element raiz, Document document, TournamentDTO tournamentDTO) {
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
        Text eleWinnerText = document.createTextNode(String.valueOf(tournamentDTO.getTWinner()));
        eleWinner.appendChild(eleWinnerText);

        eleTorneo.appendChild(eleWinner);

        Element eleCombate = document.createElement("combates");
        for (CombatDTO c : tournamentDTO.getCombats()) {
            Element combat = document.createElement("combate");

            Element date = document.createElement("date");
            Element trainer_1 = document.createElement("trainer_1");
            Element trainer_2 = document.createElement("trainer_2");
            Element winner = document.createElement("combatWinner");

            Text dateText = document.createTextNode(c.getDate());
            Text trainer_1Text = document.createTextNode(c.getTrainer_1().getName());
            Text trainer_2Text = document.createTextNode(c.getTrainer_2().getName());
            Text winnerText = document.createTextNode(c.getC_winner().getName());

            date.appendChild(dateText);
            trainer_1.appendChild(trainer_1Text);
            trainer_2.appendChild(trainer_2Text);
            winner.appendChild(winnerText);

            combat.appendChild(date);
            combat.appendChild(trainer_1);
            combat.appendChild(trainer_2);
            combat.appendChild(winner);

            eleCombate.appendChild(combat);

            eleTorneo.appendChild(combat);
        }
    }
}
