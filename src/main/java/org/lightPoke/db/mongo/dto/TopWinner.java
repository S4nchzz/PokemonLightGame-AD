package org.lightPoke.db.mongo.dto;

import org.springframework.data.mongodb.core.mapping.Field;
import jakarta.persistence.Id;

public class TopWinner {
    @Id
    private Integer id;

    @Field("trainerInfo")
    private TrainerInfo trainerInfo;

    @Field("totalVictories")
    private Integer totalVictories;

    public TopWinner(Integer id, TrainerInfo trainerInfo, Integer totalVictories) {
        this.id = id;
        this.trainerInfo = trainerInfo;
        this.totalVictories = totalVictories;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TrainerInfo getTrainerInfo() {
        return trainerInfo;
    }

    public void setTrainerInfo(TrainerInfo trainerInfo) {
        this.trainerInfo = trainerInfo;
    }

    public Integer getTotalVictories() {
        return totalVictories;
    }

    public void setTotalVictories(Integer totalVictories) {
        this.totalVictories = totalVictories;
    }

    public static class TrainerInfo {
        @Field("id")
        private Integer id;

        @Field("username")
        private String username;

        @Field("nationality")
        private String nationality;

        @Field("license")
        private License license;

        public TrainerInfo(Integer id, String username, String nationality, License license) {
            this.id = id;
            this.username = username;
            this.nationality = nationality;
            this.license = license;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
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

        public License getLicense() {
            return license;
        }

        public void setLicense(License license) {
            this.license = license;
        }
    }

    public static class License {
        @Field("id")
        private Integer id;

        @Field("expeditionDate")
        private String expeditionDate;

        @Field("points")
        private Integer points;

        @Field("nVictories")
        private Integer nVictories;

        public License(Integer id, String expeditionDate, Integer points, Integer nVictories) {
            this.id = id;
            this.expeditionDate = expeditionDate;
            this.points = points;
            this.nVictories = nVictories;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getExpeditionDate() {
            return expeditionDate;
        }

        public void setExpeditionDate(String expeditionDate) {
            this.expeditionDate = expeditionDate;
        }

        public Integer getPoints() {
            return points;
        }

        public void setPoints(Integer points) {
            this.points = points;
        }

        public Integer getnVictories() {
            return nVictories;
        }

        public void setnVictories(Integer nVictories) {
            this.nVictories = nVictories;
        }
    }
}
