package org.lightPoke.auth.nacionality;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

/**
 * Clase que usando JAXBContext y unmarshaller lee el archivo
 * xml descrito en el metodo getCountriesList para asi retornar
 * un Objeto de tipo Paises el cual contiene una lista de tipo Pais
 *
 * @author Iyan Sanchez da Costa
 */
public class PaisesLoader {
    public static List<Pais> getCountriesList() {
        try {
            JAXBContext context = JAXBContext.newInstance(Paises.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return ((Paises) unmarshaller.unmarshal(new File("./src/main/resources/src/paises.xml"))).getPaises();
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;
    }
}