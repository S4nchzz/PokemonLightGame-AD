package org.lightPoke.menus;

import org.lightPoke.Game;
import org.lightPoke.db.dto.TournamentDTO;
import org.lightPoke.db.dto.TrainerDTO;
import org.lightPoke.db.services.TournamentService;
import org.lightPoke.log.LogManagement;
import org.lightPoke.tournament.Tournament;
import org.lightPoke.tournament.TournamentList;
import org.lightPoke.users.TRUser;
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
public class TrainerMenu {
    private final LogManagement log;

    /**
     * Constructor que le pasan por parametro un entrenador, este
     * entrenador sera el entrenador con el que inicio sesion ya
     * que el metodo que instancia esta clase es el que crea dicho
     * objeto, la comparacion de NULL es hecha ya que si un usuario
     * se registra automaticamente inicia sesion y se le pasa un
     * objeto de tipo TRUser para proseguir con la informacion de
     * este usuario, en cambio si el usuario se logea con este
     * entrenador, al no tener guardado el nombre y la nacionalidad
     * sera de tipo null, lo cual no permitira continuar con la
     * configuracion de este entrenador.
     *
     * @param trainerDTO Entrenador que se ha iniciado sesion
     */
    public TrainerMenu(final TrainerDTO trainerDTO) {
        log = LogManagement.getInstance();
        Scanner sc = new Scanner(System.in);


        if (trainerDTO != null) {
            log.writeLog("Trainer " + trainerDTO.getName() + " log in succesfully");

            if (trainerDTO.getTrainerTournamentList().isEmpty()) {
                // Mostrar todos los torneos y decirle que se meta a uno
                TournamentService tournamentService = TournamentService.getInstance();
                List<TournamentDTO> tournaments = tournamentService.getAllTournaments();

                if (tournaments.size() > 0) {
                    int index = 1;
                    int choice;
                    do {
                        System.out.println("----- Your first tournament ---- \n");
                        for (TournamentDTO dto : tournaments) {
                            System.out.println(index + " - | " + dto.getName() + " | " + dto.getRegion() + " | " + dto.getVictoryPoints());
                            index++;
                        }

                        System.out.print("?: ");
                    } while (((choice = sc.nextInt()) < 1 || choice > tournaments.size()));

                    // Añadir usuario al torneo
                    TournamentDTO tournamentChoiced = tournaments.get(choice - 1);
                    tournamentService.addTrainerToTournament(trainerDTO, tournamentChoiced);
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
            case 1 -> exportLicense(null); //! MODIFICAR METODO PAR QUE FUNCIONE CON EL DTO
            case 2 -> Game.main(null);
        }
    }

    /**
     * Este metodo permitira exportar un License.class en formato XML
     * usando DOM
     *
     * @param trainer Entrenador que ha iniciado sesion y ha solicitado que se le exporte el carnet
     */
    private void exportLicense(TRUser trainer) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, "carnet", null);

            insertData(document.getDocumentElement(), document, trainer);

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
    private void insertData(Element raiz, Document docu, TRUser trainer) {
        Element eleIdEntrenador = generateElement("id", String.valueOf(trainer.getCarnet().getIdEntrenador()), docu);
        raiz.appendChild(eleIdEntrenador);

        Element eleFechaExp = generateElement("fechaexp", trainer.getCarnet().getFechaExpedicion(), docu);
        raiz.appendChild(eleFechaExp);

        Element eleEntrenador = generateElement("entrenador", null, docu);
        eleEntrenador.appendChild(generateElement("nombre", trainer.getNombre(), docu));
        eleEntrenador.appendChild(generateElement("nacionalidad", trainer.getNacionalidad(), docu));

        raiz.appendChild(eleEntrenador);

        LocalDate date = LocalDate.now();
        final String formatedDate = date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear();

        Element eleHoy = generateElement("hoy", formatedDate, docu);
        raiz.appendChild(eleHoy);

        Element elePuntos = generateElement("puntos", String.valueOf(trainer.getCarnet().getPuntos()), docu);
        raiz.appendChild(elePuntos);

        Element eleTorneos = generateElement("torneos", null, docu);
        raiz.appendChild(eleTorneos);

        TournamentList tournamentList = TournamentList.getInstance();
        for (Tournament tour : tournamentList.getTournamentList()) {
            Element eleTorneo = null;
            for (TRUser truser : tour.getTrainers()) {
                if (truser.getId() == trainer.getId() && truser.getUsername().equalsIgnoreCase(trainer.getUsername())) {
                    eleTorneo = generateElement("torneo", null, docu);

                    eleTorneo.appendChild(generateElement("nombre", tour.getNombre(), docu));
                    eleTorneo.appendChild(generateElement("region", String.valueOf(tour.getCodRegion()), docu));

                    // ! Here must be added the combats
                }
            }

            if (eleTorneo != null) {
                eleTorneos.appendChild(eleTorneo);
            }
        }

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