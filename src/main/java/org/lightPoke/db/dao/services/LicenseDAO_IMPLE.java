package org.lightPoke.db.dao.services;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.lightPoke.db.dao.interfaces.LicenseDAO_IFACE;
import org.lightPoke.db.entities.Entity_License;
import org.lightPoke.log.LogManagement;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Properties;

public class LicenseDAO_IMPLE implements LicenseDAO_IFACE {
    private final LogManagement log;
    private static LicenseDAO_IMPLE instance;
    private DataSource source;

    private LicenseDAO_IMPLE() {
        Properties props = new Properties();
        FileInputStream fis = null;
        MysqlDataSource source = null;
        log = LogManagement.getInstance();

        try {
            fis = new FileInputStream("src/main/resources/db/DB_PROPS.txt");
            props.load(fis);
            source = new MysqlDataSource();
            source.setURL(props.getProperty("MYSQL_DB_URL"));
            source.setUser(props.getProperty("MYSQL_DB_USERNAME"));
            source.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));

            this.source = source;
        } catch (IOException e) {
            log.writeLog("Unable to find file DB_PROPS.txt on the specified path");
        }
    }

    public static LicenseDAO_IMPLE getInstance() {
        if (instance == null) {
            instance = new LicenseDAO_IMPLE();
        }

        return instance;
    }

    @Override
    public Entity_License createLicense() {
        try {
            Connection conn = source.getConnection();
            PreparedStatement getLastId = conn.prepareStatement("SELECT ID FROM LICENSE ORDER BY ID DESC LIMIT 1");
            ResultSet lastId = getLastId.executeQuery();

            int nextId = -1;
            if (lastId.next()) {
                nextId = lastId.getInt("ID");
            }

            lastId.close();
            getLastId.close();

            PreparedStatement addLicense = conn.prepareStatement("INSERT INTO LICENSE (ID, EXPEDITION_DATE, POINTS, N_VICTORIES) VALUES(?, ?, ?, ?)");

            LocalDate l = LocalDate.now();
            final String currentDate = l.getDayOfMonth() + "/" + l.getMonthValue() + "/" + l.getYear();
            addLicense.setInt(1, nextId + 1);
            addLicense.setString(2, currentDate);
            addLicense.setFloat(3, 0.0f);
            addLicense.setInt(4, 0);

            addLicense.executeUpdate();

            addLicense.close();
            conn.close();

            return new Entity_License(nextId + 1, currentDate, 0, 0);
        } catch (SQLException e) {
            log.writeLog("Unable to establish a connection with the DataSource on CreateTrainer function");
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public Entity_License getLicenseByUsername(final String username) {
        try {
            Connection conn = source.getConnection();
            PreparedStatement trainer = conn.prepareStatement("SELECT * FROM TRAINER WHERE USERNAME = ?");

            trainer.setString(1, username);

            ResultSet trainerRs = trainer.executeQuery();

            int idLicenseOfTrainer = -1;
            if (trainerRs.next()) {
                idLicenseOfTrainer = trainerRs.getInt("LICENSE_ID");
            }

            if (idLicenseOfTrainer == -1) {
                log.writeLog("User " + username + " doesn't have a license");
            }

            PreparedStatement findLicense = conn.prepareStatement("SELECT * FROM LICENSE WHERE ID = ?");
            findLicense.setInt(1, idLicenseOfTrainer);

            ResultSet findLicenseRs = findLicense.executeQuery();

            Entity_License entityLicense = null;
            if (findLicenseRs.next()) {
                entityLicense = new Entity_License(findLicenseRs.getInt("ID"), findLicenseRs.getString("EXPEDITION_DATE"), findLicenseRs.getFloat("POINTS"), findLicenseRs.getInt("N_VICTORIES"));
            }

            conn.close();
            return entityLicense;
        } catch (SQLException e) {
            log.writeLog("Unable to establish a connection with the DataSource on CreateTrainer function");
        }

        return null;
    }

    @Override
    public Entity_License getLicenseByTrainerId(int trainerId) {
        try {
            Connection conn = source.getConnection();
            PreparedStatement trainer = conn.prepareStatement("SELECT * FROM TRAINER WHERE TRAINER.ID = ?");

            trainer.setInt(1, trainerId);

            ResultSet trainerRs = trainer.executeQuery();

            int idLicenseOfTrainer = -1;
            if (trainerRs.next()) {
                idLicenseOfTrainer = trainerRs.getInt("LICENSE_ID");
            }

            if (idLicenseOfTrainer == -1) {
                log.writeLog("User " + trainerId + " doesn't have a license");
            }

            PreparedStatement findLicense = conn.prepareStatement("SELECT * FROM LICENSE WHERE ID = ?");
            findLicense.setInt(1, idLicenseOfTrainer);

            ResultSet findLicenseRs = findLicense.executeQuery();

            Entity_License entityLicense = null;
            if (findLicenseRs.next()) {
                entityLicense = new Entity_License(findLicenseRs.getInt("ID"), findLicenseRs.getString("EXPEDITION_DATE"), findLicenseRs.getFloat("POINTS"), findLicenseRs.getInt("N_VICTORIES"));
            }

            conn.close();
            return entityLicense;
        } catch (SQLException e) {
            log.writeLog("Unable to establish a connection with the DataSource on CreateTrainer function");
        }

        return null;
    }
}
