package lt.viko.eif.savaitgalis.gpd.repos;

import lt.viko.eif.savaitgalis.gpd.pojo.Cases;
import lt.viko.eif.savaitgalis.gpd.pojo.Country;
import lt.viko.eif.savaitgalis.gpd.transformer.ResponseToPojo;

import java.sql.*;

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
     * Constructor which calls connectToDB method and saves the connection.
     */
    public CovidApiRepository() {

        this.conn = connectToDB();
    }

    //CONNECTION-------------------------------------

    /**
     * Method for establishing a connection to the database.
     *
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

    /**
     * Method for determining whether a record with given details exists in the database or not.
     *
     * @param country country name to check
     * @param targetDate data date
     * @return record with given details exists boolean
     */
    private boolean countryIsCached(String country, String targetDate) {

        int intTargetDate = Integer.parseInt(targetDate.replaceAll("-", ""));
        String sql = "SELECT COUNT() AS num FROM `cache` WHERE " +
                ResponseToPojo.sqlCountry + " = ? AND " +
                ResponseToPojo.sqlDate + " = ?";
        try {
            PreparedStatement prepStat = conn.prepareStatement(sql);

            prepStat.setString(1, country);
            prepStat.setInt(2, intTargetDate);

            ResultSet rs = prepStat.executeQuery();
            while (rs.next()) {
                if(rs.getInt("num")>=1)
                    return true;
            }
        } catch (SQLException e) {
            //System.out.println(e.getMessage());
        }
        return false;
    }

    //FAVOURITES-------------------------------------
}
