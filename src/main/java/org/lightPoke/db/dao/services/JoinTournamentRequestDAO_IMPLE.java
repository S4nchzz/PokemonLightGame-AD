package org.lightPoke.db.dao.services;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.lightPoke.db.dao.interfaces.JoinTournamentRequestDAO_IFACE;
import org.lightPoke.db.entities.Entity_JoinTournamentRequest;
import org.lightPoke.log.LogManagement;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JoinTournamentRequestDAO_IMPLE implements JoinTournamentRequestDAO_IFACE {
    private static JoinTournamentRequestDAO_IMPLE instance;
    private LogManagement log;
    private DataSource source;

    private JoinTournamentRequestDAO_IMPLE() {
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

    public static JoinTournamentRequestDAO_IMPLE getInstance() {
        if (instance == null) {
            instance = new JoinTournamentRequestDAO_IMPLE();
        }

        return instance;
    }

    @Override
    public void addRequestFromUser(int trainerId, int tournamentId) {
        try {
            Connection conn = source.getConnection();
            PreparedStatement st = conn.prepareStatement("INSERT INTO JOIN_TOURNAMENT_REQUEST(TRAINER_ID, TOURNAMENT_ID) VALUES (?, ?)");

            st.setInt(1, trainerId);
            st.setInt(2, tournamentId);

            st.executeUpdate();

            st.close();
            conn.close();
        } catch (SQLException e) {
            log.writeLog("Unnable to establish a connection with the DataSource on addCmobatsToTournaments() function");
        }
    }

    @Override
    public List<Entity_JoinTournamentRequest> getRequestsByTournamentId(int t_id) {
        try {
            Connection conn = source.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM JOIN_TOURNAMENT_REQUEST WHERE TOURNAMENT_ID = ?");

            st.setInt(1, t_id);

            ResultSet rs = st.executeQuery();

            List<Entity_JoinTournamentRequest> entityList = new ArrayList<>();
            while (rs.next()) {
                entityList.add(new Entity_JoinTournamentRequest(rs.getInt("ID"), rs.getInt("TRAINER_ID"), rs.getInt("TOURNAMENT_ID")));
            }

            rs.close();
            st.close();
            conn.close();

            return entityList;
        } catch (SQLException e) {
            log.writeLog("Unnable to establish a connection with the DataSource on addCmobatsToTournaments() function");
        }

        return null;
    }
}
