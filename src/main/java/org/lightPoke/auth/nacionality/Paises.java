package org.lightPoke.auth.nacionality;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa el elemento padre de un XML
 * el cual JAXB podra leer y dado el metodo getPaises
 * obtendra los valores de cada una de las etiquetas
 * "pais" dando como resultado una lista de paises
 * @author Iyan Sanchez da Costa
 */
@XmlRootElement(name = "paises")
public class Paises {
    private List<Pais> paises;

    public Paises() {
        this.paises = new ArrayList<>();
    }

    @XmlElement(name = "pais")
    public List<Pais> getPaises() {
        return paises;
    }

    public void setPaises(List<Pais> paises) {
        this.paises = paises;
    }
}
