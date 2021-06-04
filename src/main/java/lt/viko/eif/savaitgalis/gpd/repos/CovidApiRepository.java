package lt.viko.eif.savaitgalis.gpd.repos;

import lt.viko.eif.savaitgalis.gpd.api.CovidApiHandler;
import lt.viko.eif.savaitgalis.gpd.pojo.Country;
import lt.viko.eif.savaitgalis.gpd.transformer.ResponseToPojo;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A Covid API repository class.
 *
 * @author Ersidas Baniulis PI19B
 * @version 1.0
 * @since 1.0
 */
public class CovidApiRepository {

    public static final String DB_URL = "jdbc:sqlite:gpdDatabase.db";
    private static Connection conn;

    /**
     * Constructor which calls connectToDB method and saves the connection.
     */
    public CovidApiRepository() {
        if (this.conn == null)
            this.conn = connectToDB();
    }

    //CONNECTION-------------------------------------

    /**
     * Method for establishing a connection to the database.
     *
     * @return database connection conn
     */
    private Connection connectToDB() {

        try {
            return DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //CACHE------------------------------------------

    /**
     * Method for determining whether a record with given details exists in the database or not.
     *
     * @param country country name to check
     * @param targetDate data date
     * @return true if record exists, false if not
     */
    public boolean countryIsCached(String country, String targetDate) {

        int intTargetDate = Integer.parseInt(targetDate.replaceAll("-", ""));

        String sql = "SELECT COUNT() AS num FROM `cache` WHERE " +
                ResponseToPojo.SQL_COUNTRY + " = ? AND " +
                ResponseToPojo.SQL_DATE + " = ?";
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
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method which removes expired data from the database.
     */
    private void removeExpiredData() {

        int intTodayDate = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new java.util.Date()));

        String sql = "DELETE FROM `cache` WHERE expiration_date <= ?";
        try {
            PreparedStatement prepStat  = conn.prepareStatement(sql);

            prepStat.setInt(1, intTodayDate);

            prepStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
                ResponseToPojo.SQL_COUNTRY + " = ? AND " +
                ResponseToPojo.SQL_DATE + " = ?";
        try {
            PreparedStatement prepStat = conn.prepareStatement(sql);

            prepStat.setString(1, country);
            prepStat.setInt(2, intTargetDate);

            ResultSet rs = prepStat.executeQuery();
            while (rs.next()) {
                return ResponseToPojo.responseFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
                ResponseToPojo.SQL_COUNTRY + ", " +
                ResponseToPojo.SQL_DATE + ", " +
                ResponseToPojo.SQL_TESTS + ", " +
                ResponseToPojo.SQL_CASES_NEW + ", " +
                ResponseToPojo.SQL_CASES_ACTIVE + ", " +
                ResponseToPojo.SQL_CASES_CRITICAL + ", " +
                ResponseToPojo.SQL_CASES_RECOVERED + ", " +
                ResponseToPojo.SQL_CASES_TOTAL + ", " +
                ResponseToPojo.SQL_DEATHS_NEW + ", " +
                ResponseToPojo.SQL_DEATHS_TOTAL + ", " +
                "expiration_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement prepStat  = conn.prepareStatement(sql);

            prepStat.setString(1, country.getCountry());
            prepStat.setInt(2, intTargetDate);
            prepStat.setInt(3, country.getTests());
            prepStat.setInt(4, country.getCases().getNewCases());
            prepStat.setInt(5, country.getCases().getActive());
            prepStat.setInt(6, country.getCases().getCritical());
            prepStat.setInt(7, country.getCases().getRecovered());
            prepStat.setInt(8, country.getCases().getTotal());
            prepStat.setInt(9, country.getDeaths().getNewDeaths());
            prepStat.setInt(10, country.getDeaths().getTotalDeaths());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            Calendar c = Calendar.getInstance();
            c.setTime(simpleDateFormat.parse(simpleDateFormat.format(new java.util.Date())));
            c.add(Calendar.DATE, 1);

            prepStat.setInt(11, Integer.parseInt(simpleDateFormat.format(c.getTime())));

            prepStat.executeUpdate();
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }


    /**
     * Method which returns a Country object with given details.
     * Returns the object from the database if record with given details exists,
     * else returns the object from COVID-19 API [https://rapidapi.com/api-sports/api/covid-193].
     *
     * @param country country name
     * @param targetDate data date
     * @return Country object
     */
    public Country getCountry(String country, String targetDate) {
        removeExpiredData();

        country = country.toLowerCase();

        if(targetDate.equalsIgnoreCase("today")) {
            targetDate = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        }
        else {
            try {
                Integer.parseInt(targetDate.replaceAll("-", ""));
            } catch (Exception e) {
                return  null;
            }
        }

        if(countryIsCached(country, targetDate)) {
            return getCachedCountry(country, targetDate);
        }
        targetDate = new StringBuilder(targetDate.replaceAll("-", "")).insert(4, "-").insert(7, "-").toString();
        Country resultCountry = CovidApiHandler.getCountryData(country, targetDate);
        if(resultCountry != null) {
            cacheCountry(resultCountry, targetDate);
        }
        return resultCountry;
    }

    //FAVOURITES-------------------------------------

    /**
     * Method for determining whether a record with given details exists in the database or not.
     *
     * @param country country name to check
     * @return true if record exists, false if not
     */
    private boolean favouriteCountryExists(String country) {

        String sql = "SELECT COUNT() AS num FROM `favourite` WHERE country = ?";
        try {
            PreparedStatement prepStat = conn.prepareStatement(sql);

            prepStat.setString(1, country);

            ResultSet rs = prepStat.executeQuery();
            while (rs.next()) {
                if(rs.getInt("num")>=1)
                    return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method which inserts the given country name into the database.
     *
     * @param country country name to insert
     */
    public void addFavouriteCountry(String country) {

        country = country.toLowerCase();

        if(favouriteCountryExists(country))
            return;

        String sql = "INSERT INTO `favourite` (country) VALUES (?)";

        try {
            PreparedStatement prepStat  = conn.prepareStatement(sql);

            prepStat.setString(1, country);

            prepStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method which deletes given country name from the database.
     *
     * @param country country name to delete
     */
    public void removeFavouriteCountry(String country) {

        country = country.toLowerCase();

        String sql = "DELETE FROM `favourite` WHERE country = ?";
        try {
            PreparedStatement prepStat  = conn.prepareStatement(sql);

            prepStat.setString(1, country);

            prepStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method which returns a list of all favourite country names from the database.
     *
     * @return list of favourite country names
     */
    private List<String> getFavouriteNamesList() {

        List<String> favouriteNamesList = new ArrayList<>();
        String sql = "SELECT * FROM `favourite`";
        try {
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()) {
                favouriteNamesList.add(rs.getString("country"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(favouriteNamesList.size() == 0)
            return null;
        return favouriteNamesList;
    }

    /**
     * Method which returns a list of favourite Country objects from the database.
     *
     * @return list of Country objects
     */
    public List<Country> getFavouriteCountryStats() {

         List<Country> favouriteCountryStats = new ArrayList<>();
         try {
             for(String s : getFavouriteNamesList()) {
                 favouriteCountryStats.add(getCountry(s, "today"));
             }
             return favouriteCountryStats;
         } catch(Exception e) {
             return null;
         }
    }
}
