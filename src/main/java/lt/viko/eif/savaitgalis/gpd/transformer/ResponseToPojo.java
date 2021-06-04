package lt.viko.eif.savaitgalis.gpd.transformer;

import lt.viko.eif.savaitgalis.gpd.pojo.Cases;
import lt.viko.eif.savaitgalis.gpd.pojo.Country;
import lt.viko.eif.savaitgalis.gpd.pojo.Deaths;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.validation.constraints.Null;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

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
    public static final String SQL_COUNTRY = "country";
    public static final String SQL_DATE = "date";
    public static final String SQL_TESTS = "tests";
    public static final String SQL_CASES_NEW = "cases_new";
    public static final String SQL_CASES_ACTIVE = "cases_active";
    public static final String SQL_CASES_CRITICAL = "cases_critical";
    public static final String SQL_CASES_RECOVERED = "cases_recovered";
    public static final String SQL_CASES_TOTAL = "cases_total";
    public static final String SQL_DEATHS_NEW = "deaths_new";
    public static final String SQL_DEATHS_TOTAL = "deaths_total";

    /**
     * Creates a {@link Country} object from API response.
     *
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

        returnCountry.setCountry(newestResponse.get("country").toString().toLowerCase());
        returnCountry.setDate(Integer.parseInt(newestResponse.get("day").toString().replaceAll("-", "")));

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

        try {
            JSONObject testsObject = (JSONObject) newestResponse.get("tests");
            returnCountry.setTests(Integer.parseInt(testsObject.get("total").toString()));

        } catch (NullPointerException exception) {
            returnCountry.setTests(-1);
        }

        return returnCountry;
    }

    /**
     * Creates a {@link Country} object from the ResultSet of SQLite query. It doesn't loop. Returns only 1 object
     *
     * @param resultSet the result set pointer
     * @return a Country object filled with data from the result
     * @throws SQLException SQLite getInt or getString failed
     */
    public static Country responseFromResultSet(ResultSet resultSet) throws SQLException {
        Cases cases = new Cases(resultSet.getInt(SQL_CASES_NEW), resultSet.getInt(SQL_CASES_ACTIVE), resultSet.getInt(SQL_CASES_CRITICAL), resultSet.getInt(SQL_CASES_RECOVERED), resultSet.getInt(SQL_CASES_TOTAL));
        Deaths deaths = new Deaths(resultSet.getInt(SQL_DEATHS_NEW), resultSet.getInt(SQL_DEATHS_TOTAL));
        return new Country(resultSet.getString(SQL_COUNTRY), resultSet.getInt(SQL_DATE), resultSet.getInt(SQL_TESTS), cases, deaths);
    }
}
