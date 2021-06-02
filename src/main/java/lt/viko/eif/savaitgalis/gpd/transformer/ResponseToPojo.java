package lt.viko.eif.savaitgalis.gpd.transformer;

import lt.viko.eif.savaitgalis.gpd.pojo.Cases;
import lt.viko.eif.savaitgalis.gpd.pojo.Country;
import lt.viko.eif.savaitgalis.gpd.pojo.Deaths;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A transformer class. That transforms JSON response from the API or SQLite result to {@link Country}.
 *
 * @author Emilis Margevičius PI19B
 * @version 1.0
 * @since 1.0
 * @see Country
 */
public class ResponseToPojo {
    // SQLite table fields used by transformFromResultSet
    public static final String sqlCountry = "country";
    public static final String sqlDate = "date";
    public static final String sqlTests = "tests";
    public static final String sqlCasesNew = "cases_new";
    public static final String sqlCasesActive = "cases_active";
    public static final String sqlCasesCritical = "cases_critical";
    public static final String sqlCasesRecovered = "cases_recovered";
    public static final String sqlCasesTotal = "cases_total";
    public static final String sqlDeathsNew = "deaths_new";
    public static final String sqlDeathsTotal = "deaths_total";

    /**
     * Creates a {@link Country} object from API response.
     * @param responseBody a JSON string that we get from the API
     * @return a {@link Country} object filled with responseBody Data
     * @throws ParseException JSON parsing exception
     * @see Country
     */
    public static Country transformFromResponse(String responseBody) throws ParseException {
        JSONParser parse = new JSONParser();

        JSONObject jsonObject = (JSONObject)parse.parse(responseBody);


        Country returnCountry = new Country();

        JSONArray responseArray = (JSONArray)jsonObject.get("response");
        JSONObject newestResponse;
        try{
            newestResponse = (JSONObject)responseArray.get(0);
        } catch (Exception err) {
            return null;
        }

        returnCountry.setCountry(newestResponse.get("country").toString());
        returnCountry.setDate(newestResponse.get("day").toString());

        JSONObject casesObject = (JSONObject)newestResponse.get("cases");
        Cases cases = new Cases();
        cases.setNewCases(Integer.parseInt(casesObject.get("new").toString().substring(1)));
        cases.setActive(Integer.parseInt(casesObject.get("active").toString()));
        cases.setCritical(Integer.parseInt(casesObject.get("critical").toString()));
        cases.setRecovered(Integer.parseInt(casesObject.get("recovered").toString()));
        cases.setTotal(Integer.parseInt(casesObject.get("total").toString()));
        returnCountry.setCases(cases);

        JSONObject deathsObject = (JSONObject)newestResponse.get("deaths");
        Deaths deaths = new Deaths();
        deaths.setNewDeaths(Integer.parseInt(deathsObject.get("new").toString().substring(1)));
        deaths.setTotalDeaths(Integer.parseInt(deathsObject.get("total").toString()));
        returnCountry.setDeaths(deaths);

        JSONObject testsObject = (JSONObject)newestResponse.get("tests");
        returnCountry.setTests(Integer.parseInt(testsObject.get("total").toString()));

        return returnCountry;
    }

    /**
     * Creates a {@link Country} object from the ResultSet of SQLite query. It doesn't loop. Returns only 1 object
     *
     * Implementation should look something like:
     *
     * loop through result set:
     *      Country country is responseFromResultSet(result set)
     *
     * @param resultSet the result set pointer
     * @return a Country object filled with data from the result
     * @throws SQLException SQLite getInt or getString failed
     */
    public static Country responseFromResultSet(ResultSet resultSet) throws SQLException {
        Cases cases = new Cases(resultSet.getInt(sqlCasesNew), resultSet.getInt(sqlCasesActive), resultSet.getInt(sqlCasesCritical), resultSet.getInt(sqlCasesRecovered), resultSet.getInt(sqlCasesTotal));
        Deaths deaths = new Deaths(resultSet.getInt(sqlDeathsNew), resultSet.getInt(sqlDeathsTotal));
        return new Country(resultSet.getString(sqlCountry), resultSet.getString(sqlDate), resultSet.getInt(sqlTests), cases, deaths);
    }
}
