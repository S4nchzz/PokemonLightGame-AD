package org.lightPoke.db.dao.services;

import org.lightPoke.db.dao.interfaces.TrainerDAO_IFACE;
import org.lightPoke.db.entities.Entity_Trainer;
import org.lightPoke.log.LogManagement;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.cj.jdbc.MysqlDataSource;

public class TrainerDAO_IMPLE implements TrainerDAO_IFACE {
    private static TrainerDAO_IMPLE instance;
    private DataSource source = null;
    private final LogManagement log;

    private TrainerDAO_IMPLE() {
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
            log.writeLog("Unnable to find file DB_PROPS.txt on the specified path");
        }
    }

    public static TrainerDAO_IMPLE getInstance() {
        if (instance == null) {
            instance = new TrainerDAO_IMPLE();
        }

        return instance;
    }

    @Override
    public void createTrainer(Entity_Trainer entity) {
        try {
            Connection conn = source.getConnection();
            PreparedStatement st = conn.prepareStatement("INSERT INTO TRAINER (USERNAME, NAME, NATIONALITY, LICENSE_ID) VALUES (?, ?, ?, ?)");

            st.setString(1, entity.username());
            st.setString(2, entity.name());
            st.setString(3, entity.nationality());
            st.setInt(4, entity.license());

            st.executeUpdate();

            log.writeLog("User " + entity.name() + " created");

            st.close();
            conn.close();
        } catch (SQLException e) {
            log.writeLog("Unnable to establish a connection with the DataSource on CreateTrainer function");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeTrainer(int trainerId) {
        try {
            Connection conn = source.getConnection();
            PreparedStatement st = conn.prepareStatement("DELETE FROM TRAINER WHERE ID = ?");
            st.setInt(1, trainerId);

            st.executeUpdate();

            log.writeLog("User with id: " + trainerId + " deleted");

            st.close();
            conn.close();
        } catch (SQLException e) {
            log.writeLog("Unnable to establish a connection with the DataSource on CreateTrainer function");
            throw new RuntimeException(e);
        }
    }

    @Override
    public Entity_Trainer getTrainerByUsername(final String username) {
        try {
            Connection conn = source.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM TRAINER WHERE TRAINER.USERNAME = ?");
            st.setString(1, username);

            ResultSet rs = st.executeQuery();

            Entity_Trainer entity = null;
            while (rs.next()) {
                entity = new Entity_Trainer(rs.getInt("ID"), rs.getString("USERNAME"), rs.getString("NAME"), rs.getString("NATIONALITY"), rs.getInt("LICENSE_ID"));
            }

            if (entity == null) {
                log.writeLog("For some reason, the user with username: " + username + " hasn't been found on the database");
            }

            log.writeLog("User : " + username + " founded");

            rs.close();
            st.close();
            conn.close();

            return entity;
        } catch (SQLException e) {
            log.writeLog("Unnable to establish a connection with the DataSource on CreateTrainer function");
            throw new RuntimeException(e);
        }
    }

    @Override
    public Entity_Trainer getTrainerById(int trainerId) {
        try {
            Connection conn = source.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM TRAINER WHERE TRAINER.ID = ?");
            st.setInt(1, trainerId);

            ResultSet rs = st.executeQuery();

            Entity_Trainer entity = null;
            if (rs.next()) {
                entity = new Entity_Trainer(rs.getInt("ID"), rs.getString("USERNAME"), rs.getString("NAME"), rs.getString("NATIONALITY"), rs.getInt("LICENSE_ID"));
            }

            rs.close();
            st.close();
            conn.close();

            return entity;
        } catch (SQLException e) {
            log.writeLog("Unnable to establish a connection with the DataSource on getTrainerById function");
            throw new RuntimeException(e);
        }
    }
}
