package org.lightPoke.menus;

import org.lightPoke.log.LogManagement;
import org.lightPoke.tournament.Tournament;
import org.lightPoke.tournament.TournamentList;
import org.lightPoke.trainerLicense.License;
import org.lightPoke.users.TRUser;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class TrainerMenu {
    private final LogManagement log;
    public TrainerMenu(final TRUser trainer) {
        log = LogManagement.getInstance();

        if (trainer != null) {
            log.writeLog("Trainer has info (he has registered and autoLogged)");

            // Show tournaments
            Scanner sc = new Scanner(System.in);
            TournamentList tournametList = TournamentList.getInstance();

            if (tournametList.size() > 0) {
                System.out.println("------ Tu Primer Torneo ------");
                System.out.println(tournametList.listTournaments());
                System.out.print("?: ");

                int tChoice;
                while ((tChoice = sc.nextInt()) < 1 || tChoice > tournametList.size()) {
                    System.out.println("Torneo no encontrado, elija un torneo entre 1 y " + tournametList.size());
                    System.out.println(tournametList.listTournaments());
                }
                tournametList.addTrainer(trainer, tChoice);
            }

            System.out.println(tournametList);

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
                    exportLicense(trainer);
                }
                case 2 -> {}
            }
        } else {
            log.writeLog("Trainer has no info (login before registered)");
        }
    }

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

    private void insertData(Element raiz, Document docu, TRUser trainer) {
        Element eleIdEntrenador = generateElement("id", String.valueOf(trainer.getCarnet().getIdEntrenador()), docu);
        raiz.appendChild(eleIdEntrenador);

        Element eleFechaExp = generateElement("fechaexp", trainer.getCarnet().getFechaExpedicion(), docu);
        raiz.appendChild(eleFechaExp);

        Element eleEntrenador = generateElement("entrenador", null, docu);
        eleEntrenador.appendChild(generateElement("nombre", trainer.getNombre(), docu));
        eleEntrenador.appendChild(generateElement("nacionalidad", trainer.getNacionalidad(), docu));

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
            for (TRUser truser : tour.getTrainers()) {
                if (truser.getId() == trainer.getId()) {
                    eleTorneos.appendChild(generateElement("nombre", tour.getNombre(), docu));
                    eleTorneos.appendChild(generateElement("region", String.valueOf(tour.getCodRegion()), docu));

                    // ! Here must be added the combats
                }
            }
        }
    }

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