package org.lightPoke.db.dao.services;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.lightPoke.db.dao.interfaces.At_InTournamentDAO_IFACE;
import org.lightPoke.db.entities.Entity_AT_InTournament;
import org.lightPoke.log.LogManagement;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class At_InTournamentDAO_IMPLE implements At_InTournamentDAO_IFACE {
    private static At_InTournamentDAO_IMPLE instance;
    private LogManagement log;
    private DataSource source;

    private At_InTournamentDAO_IMPLE() {
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

    public static At_InTournamentDAO_IMPLE getInstance() {
        if (instance == null) {
            instance = new At_InTournamentDAO_IMPLE();
        }
        return instance;
    }

    @Override
    public void createTournamentAdmin(final String adminUsername, final int tournament_id) {
        try {
            Connection conn = source.getConnection();
            PreparedStatement st = conn.prepareStatement("INSERT INTO AT_IN_TOURNAMENT (USERNAME, TOURNAMENT_ID) VALUES (?, ?)");
            st.setString(1, adminUsername);
            st.setInt(2, tournament_id);

            st.executeUpdate();

            st.close();
            conn.close();
        } catch (SQLException e) {
            log.writeLog("Unnable to establish a connection with the DataSource on CreateTrainer function");
            throw new RuntimeException(e);
        }
    }

    public Entity_AT_InTournament getAt_InTournamentEntityByAdminUsername(String username) {
        try {
            Connection conn = source.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM AT_IN_TOURNAMENT WHERE USERNAME = ?");
            st.setString(1, username);

            ResultSet rs = st.executeQuery();

            Entity_AT_InTournament entity = null;
            if (rs.next()) {
                entity = new Entity_AT_InTournament(rs.getInt("ID"), rs.getString("USERNAME"), rs.getInt("TOURNAMENT_ID"));
            }

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
    public boolean userExistInDatabase(final String username) {
        try {
            Connection conn = source.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM AT_IN_TOURNAMENT WHERE USERNAME = ?");
            st.setString(1, username);

            ResultSet rs = st.executeQuery();

            boolean userFounded = rs.next();

            rs.close();
            st.close();
            conn.close();

            return userFounded;
        } catch (SQLException e) {
            log.writeLog("Unnable to establish a connection with the DataSource on CreateTrainer function");
            throw new RuntimeException(e);
        }
    }
}