package lt.viko.eif.savaitgalis.gpd.repos;

import lt.viko.eif.savaitgalis.gpd.pojo.Cases;
import lt.viko.eif.savaitgalis.gpd.pojo.Country;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A Covid API repository class.
 *
 * @author Ersidas Baniulis PI19B
 * @version 1.0
 * @since 1.0
 */
public class CovidApiRepository {

    private final String DB_URL = "jdbc:sqlite:gpdDatabase.db";
    private Connection conn;

    /**
     * Constructor which calls connectToDB method and saves the connection
     */
    public CovidApiRepository() {

        this.conn = connectToDB();
    }

    //CONNECTION-------------------------------------

    /**
     * Method for establishing a connection to the database
     * @return database connection conn
     */
    private Connection connectToDB() {

        String url = DB_URL;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    //CACHE------------------------------------------

    //FAVOURITES-------------------------------------
}
