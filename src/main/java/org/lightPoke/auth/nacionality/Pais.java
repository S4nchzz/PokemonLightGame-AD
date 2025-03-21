package org.lightPoke.auth.nacionality;

import javax.xml.bind.annotation.XmlType;

/** Clase que permite a JAXB obtener los registros de un archivo XML
 * en un orden en especifico con getters y setters
 * @author Iyan Sanchez da Costa
 */
@XmlType(propOrder = {"id", "nombre"})
public class Pais {
    private String id;
    private String nombre;

    public Pais() {}

    public Pais(final String id, final String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return this.id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setId(String id) {
        this.id = id;
    }
}
