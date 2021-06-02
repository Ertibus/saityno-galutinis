package lt.viko.eif.savaitgalis.gpd.repos;

import lt.viko.eif.savaitgalis.gpd.pojo.Country;
import lt.viko.eif.savaitgalis.gpd.transformer.ResponseToPojo;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
        }
        return conn;
    }

    //CACHE------------------------------------------

    /**
     * Method for determining whether a record with given details exists in the database or not.
     *
     * @param country country name to check
     * @param targetDate data date
     * @return true if record exists, false if not
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
        }
        return false;
    }

    /**
     * Method that removes expired data from the database.
     */
    private void removeExpiredData() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        int intTodayDate = Integer.parseInt(simpleDateFormat.format(new java.util.Date()));

        String sql = "DELETE FROM `cache` WHERE expiration_date <= ?";
        try {
            PreparedStatement prepStat  = conn.prepareStatement(sql);

            prepStat.setInt(1, intTodayDate);

            prepStat.executeUpdate();
        } catch (SQLException e) {
        }
    }

    /**
     * Method which returns Country object with given details from the database.
     *
     * @param country country name
     * @param targetDate data date
     * @return Country object
     */
    private Country getCachedCountry(String country, String targetDate) {

        int intTargetDate = Integer.parseInt(targetDate.replaceAll("-", ""));

        String sql = "SELECT * FROM `cache` WHERE " +
                ResponseToPojo.sqlCountry + " = ? AND " +
                ResponseToPojo.sqlDate + " = ?";
        try {
            PreparedStatement prepStat = conn.prepareStatement(sql);

            prepStat.setString(1, country);
            prepStat.setInt(2, intTargetDate);

            ResultSet rs = prepStat.executeQuery();
            while (rs.next()) {
                return ResponseToPojo.responseFromResultSet(rs);
            }
        } catch (SQLException e) {
        }
        return null;
    }

    /**
     * Method which inserts the given Country object with the given target date into the database.
     * Sets expiration date of the record to 1 day after the method was called.
     *
     * @param country Country object to insert
     * @param targetDate data date
     */
    private void cacheCountry(Country country, String targetDate) {

        int intTargetDate = Integer.parseInt(targetDate.replaceAll("-", ""));

        String sql = "INSERT INTO `cache` ("+
                ResponseToPojo.sqlCountry + ", " +
                ResponseToPojo.sqlDate + ", " +
                ResponseToPojo.sqlTests + ", " +
                ResponseToPojo.sqlCasesNew + ", " +
                ResponseToPojo.sqlCasesActive + ", " +
                ResponseToPojo.sqlCasesCritical + ", " +
                ResponseToPojo.sqlCasesRecovered + ", " +
                ResponseToPojo.sqlCasesTotal + ", " +
                ResponseToPojo.sqlDeathsNew + ", " +
                ResponseToPojo.sqlDeathsTotal + ", " +
                "expiration_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement prepStat  = conn.prepareStatement(sql);

            prepStat.setString(1,country.getCountry());
            prepStat.setInt(2,country.getDate());
            prepStat.setInt(3,country.getTests());
            prepStat.setInt(4,country.getCases().getNewCases());
            prepStat.setInt(5,country.getCases().getActive());
            prepStat.setInt(6,country.getCases().getCritical());
            prepStat.setInt(7,country.getCases().getRecovered());
            prepStat.setInt(8,country.getCases().getTotal());
            prepStat.setInt(9,country.getDeaths().getNewDeaths());
            prepStat.setInt(10,country.getDeaths().getTotalDeaths());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            Calendar c = Calendar.getInstance();
            c.setTime(simpleDateFormat.parse(simpleDateFormat.format(new java.util.Date())));
            c.add(Calendar.DATE, 1);

            prepStat.setInt(11, Integer.parseInt(simpleDateFormat.format(c.getTime())));

            prepStat.executeUpdate();
        } catch (SQLException | ParseException e) {
        }
    }

    //FAVOURITES-------------------------------------
}
