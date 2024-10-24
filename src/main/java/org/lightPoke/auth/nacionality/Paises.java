package org.lightPoke.auth.nacionality;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Paises {
    private List<Pais> paises;

    public Paises() {
        this.paises = new ArrayList<>();
    }

    @XmlElementWrapper(name = "paises")
    @XmlElement(name = "pais")
    public List<Pais> getPaises() {
        return paises;
    }

    public void setPaises(List<Pais> paises) {
        this.paises = paises;
    }
}
