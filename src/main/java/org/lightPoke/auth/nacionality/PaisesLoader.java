package org.lightPoke.auth.nacionality;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

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