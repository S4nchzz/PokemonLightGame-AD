package org.lightPoke.db.dao.implementations;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.lightPoke.db.dao.interfaces.TournamentDAO_IFACE;
import org.lightPoke.db.entities.Entity_Tournament;
import org.lightPoke.log.LogManagement;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class TournamentDAO_IMPLE implements TournamentDAO_IFACE {
    private static TournamentDAO_IMPLE instance;
    private DataSource source = null;
    private final LogManagement log;

    private TournamentDAO_IMPLE () {
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

    public static TournamentDAO_IMPLE getInstance() {
        if (instance == null) {
            instance = new TournamentDAO_IMPLE();
        }

        return instance;
    }

    @Override
    public void createTournament(Entity_Tournament entity) {
        try {
            Connection conn = source.getConnection();
            PreparedStatement st = conn.prepareStatement("INSERT INTO TOURNAMENT (NAME, COD_REGION, VICTORY_POINTS) VALUES (?, ?, ?)");
            st.setString(1, entity.name());
            st.setString(2, String.valueOf(entity.cod_region()));
            st.setFloat(3, entity.victory_points());

            st.executeUpdate();

            log.writeLog("Tournament " + entity.name() + " created");

            st.close();
            conn.close();
        } catch (SQLException e) {
            log.writeLog("Unnable to establish a connection with the DataSource on createTournament() function");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeTournament(int tournamentId) {

    }
}
