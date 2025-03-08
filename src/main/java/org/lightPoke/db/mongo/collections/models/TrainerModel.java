package org.lightPoke.db.mongo.collections.models;

import org.springframework.data.mongodb.core.mapping.Field;

public class TrainerModel {
    @Field("id")
    private int id;

    @Field("username")
    private String username;

    @Field("nationality")
    private String nationality;

    @Field("license")
    private LicenseModel license;

    public TrainerModel() {}

    public TrainerModel(int id, String username, String nationality, LicenseModel license) {
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