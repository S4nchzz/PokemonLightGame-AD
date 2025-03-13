package org.lightPoke.db.mongo.collections;

import jakarta.persistence.Id;
import org.lightPoke.db.mongo.collections.models.LicenseModel;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "trainer")
public class TrainerCollection {
    @Id
    private int id;

    @Field("username")
    private String username;

    @Field("nationality")
    private String nationality;

    @Field("license")
    private LicenseModel license;

    public TrainerCollection() {}

    public TrainerCollection(int id, String username, String nationality, LicenseModel license) {
        this.id = id;
        this.username = username;
        this.nationality = nationality;
        this.license = license;
    }

    // Getters y setters (los añadirás tú)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public LicenseModel getLicense() {
        return license;
    }

    public void setLicense(LicenseModel license) {
        this.license = license;
    }
}
