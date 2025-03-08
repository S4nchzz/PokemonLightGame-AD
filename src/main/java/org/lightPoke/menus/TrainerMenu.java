package org.lightPoke.menus;

import org.lightPoke.db.mysql.entity.Ent_Combat;
import org.lightPoke.db.mysql.entity.Ent_JoinTournamentRequest;
import org.lightPoke.db.mysql.entity.Ent_Tournament;
import org.lightPoke.db.mysql.entity.Ent_Trainer;
import org.lightPoke.db.mysql.services.Svice_Combat;
import org.lightPoke.db.mysql.services.Svice_JoinTournamentRequest;
import org.lightPoke.db.mysql.services.Svice_Tournament;
import org.lightPoke.log.LogManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Clase que solo sera accesible cuando un usuario de tipo
 * entrenador (AGUser) inicia sesion permitiendo poder
 * exportar su propio carnet.
 *
 * @author Iyan Sanchez da Costa
 */
@Component
public class TrainerMenu {
    @Autowired
    private MainMenu mainMenu;

    private final LogManagement log;

    @Autowired
    private Svice_Tournament serviceTournament;

    @Autowired
    private Svice_Combat serviceCombat;

    @Autowired
    private Svice_JoinTournamentRequest serviceJoinTournamentRequest;

    public TrainerMenu() {
        this.log = LogManagement.getInstance();
    }

    public void openMenu(Ent_Trainer trainerEntity) {
        Scanner sc = new Scanner(System.in);

        if (trainerEntity != null) {
            log.writeLog("Trainer " + trainerEntity.getName() + " log in succesfully");

            // Mostrar torneos y preguntar cual quiere para presentar una solicitud, si estaba en algun combate querra decir que ya estaba en algun torneo
            if (!serviceCombat.isTrainerInAnyCombat(trainerEntity.getId()) && !serviceJoinTournamentRequest.trainerHasPendingRequests(trainerEntity.getId())) {
                List<Ent_Tournament> tournaments = serviceTournament.getAllTournaments();

                if (!tournaments.isEmpty()) {
                    int choice = retriveTournamentChoosed(tournaments);

                    // Añadir request del torneo
                    Ent_Tournament tournamentChoiced = tournaments.get(choice - 1);
                    serviceJoinTournamentRequest.addRequestFromTrainer(new Ent_JoinTournamentRequest(trainerEntity, tournamentChoiced));
                }
            }
        }

        System.out.println("------ Trainer menu ------");
        System.out.println("1. Exportar carnet");
        System.out.println("2. Logout");

        int choice;
        while ((choice = sc.nextInt()) != 1 && choice != 2) {
            System.out.println("------ Trainer menu ------");
            System.out.println("1. Exportar carnet");
            System.out.println("2. Logout");
        }

        switch(choice) {
            case 1 -> {
                exportLicense(trainerEntity);
                openMenu(trainerEntity);
            }
            case 2 -> {
                mainMenu.openMainMenu();
            }
        }
    }

    private int retriveTournamentChoosed(List<Ent_Tournament> tournaments) {
        Scanner sc = new Scanner(System.in);
        int index = 1;
        int choice;
        do {
            System.out.println("----- Your first tournament ---- \n");
            System.out.println("*-----* Send a request *-----*");
            for (Ent_Tournament dto : tournaments) {
                System.out.println(index + " - | " + dto.getName() + " | " + dto.getRegion() + " | " + dto.getVictoryPoints());
                index++;
            }

            System.out.print("?: ");
        } while (((choice = sc.nextInt()) < 0 || choice > tournaments.size()));

        return choice;
    }

    /**
     * Este metodo permitira exportar un License.class en formato XML
     * usando DOM
     *
     * @param trainerEntity Entrenador que ha iniciado sesion y ha solicitado que se le exporte el carnet
     */
    private void exportLicense(Ent_Trainer trainerEntity) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, "carnet", null);

            insertData(document.getDocumentElement(), document, trainerEntity);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            File resultFile = new File("./src/main/resources/users/exported_license.xml");
            Source source = new DOMSource(document);
            Result resultXML = new StreamResult(resultFile);

            transformer.transform(source, resultXML);

        } catch (ParserConfigurationException | TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo que sera llamado cuando se quiera exportar el carnet, adquiriendo
     * todos los datos del usuario y ecribiendolos en la raiz del XML usando
     * Element.class
     * @param raiz Raiz del archivo XML
     * @param docu Documento usado para la creacion de Elementos
     * @param trainer Entrenador con su informacion
     */
    private void insertData(Element raiz, Document docu, Ent_Trainer trainer) {
        Element eleIdEntrenador = generateElement("id", String.valueOf(trainer.getId()), docu);
        raiz.appendChild(eleIdEntrenador);

        Element eleFechaExp = generateElement("fechaexp", trainer.getLicense().getExpedition_Date(), docu);
        raiz.appendChild(eleFechaExp);

        Element eleEntrenador = generateElement("entrenador", null, docu);
        eleEntrenador.appendChild(generateElement("nombre", trainer.getName(), docu));
        eleEntrenador.appendChild(generateElement("nacionalidad", trainer.getNationality(), docu));

        raiz.appendChild(eleEntrenador);

        LocalDate date = LocalDate.now();
        final String formatedDate = date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear();

        Element eleHoy = generateElement("hoy", formatedDate, docu);
        raiz.appendChild(eleHoy);

        Element elePuntos = generateElement("puntos", String.valueOf(trainer.getLicense().getPoints()), docu);
        raiz.appendChild(elePuntos);

        Element eleTorneos = generateElement("torneos", null, docu);
        raiz.appendChild(eleTorneos);

        for (Ent_Tournament tour : serviceTournament.getTournamentsByUserId(trainer.getId())) {
            Element eleTorneo = generateElement("torneo", null, docu);

            eleTorneo.appendChild(generateElement("nombre", tour.getName(), docu));
            eleTorneo.appendChild(generateElement("region", String.valueOf(tour.getRegion()), docu));

            eleTorneos.appendChild(eleTorneo);
        }

        Element eleCombats = generateElement("combates", null, docu);

        for (Ent_Combat c : serviceCombat.getCombatsFinishedByTrainerId(trainer.getId())) {
            Element combat = generateElement("combat", null, docu);

            String oponentName = "none";

            if (c.getTrainer_1().getId() != trainer.getId() && c.getTrainer_2().getId() == trainer.getId()) {
                oponentName = c.getTrainer_1().getUsername();
            } else if (c.getTrainer_2().getId() != trainer.getId() && c.getTrainer_1().getId() == trainer.getId()) {
                oponentName = c.getTrainer_2().getUsername();
            }

            combat.appendChild(generateElement("id", String.valueOf(c.getId()), docu));
            combat.appendChild(generateElement("oponente", oponentName, docu));
            combat.appendChild(generateElement("fecha", c.getDate(), docu));

            String didHeWon = "no";
            if (c.getC_winner().getId() == trainer.getId()) {
                didHeWon = "si";
            }

            combat.appendChild(generateElement("victoria", didHeWon, docu));

            eleCombats.appendChild(combat);
        }

        raiz.appendChild(eleCombats);

        log.writeLog("License exported");
    }
    /**
     * Metodo que generara un Elemento y lo importa a la raiz que se le pasa como parametro
     *
     * @param elementName Nombre del elemento a crear
     * @param elementContent Contenido del elemento a crear (si es que existe)
     * @param docu Clase document para crear elementos
     * @return Nuevo elemento creado con las especificaciones adquiridas
     */
    private Element generateElement(final String elementName, final String elementContent, Document docu) {
        if (elementContent != null) {
            Element ele = docu.createElement(elementName);
            Text text = docu.createTextNode(elementContent);

            ele.appendChild(text);
            return ele;
        }

        return docu.createElement(elementName);
    }
}
