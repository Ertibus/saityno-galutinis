package lt.viko.eif.savaitgalis.gpd.api;

import lt.viko.eif.savaitgalis.gpd.pojo.Country;
import lt.viko.eif.savaitgalis.gpd.transformer.ResponseToPojo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Objects;

/**
 * Class that handles sending request to COVID-19 API [https://rapidapi.com/api-sports/api/covid-193]
 *
 * @author Emilis Margevičius PI19B
 * @version 1.0
 * @since 1.0
 * @see Country
 */
public class CovidApiHandler {
    /**
     * Send a request to COVID-19 API to get a {@link Country}
     *
     * @param country the search target. Use 'all' for global
     * @param day the target day. Format: YYYY-MM-DD
     * @return a {@link Country} object
     * @see Country
     */
    public static Country getCountryData(String country, String day){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(String.format("https://covid-193.p.rapidapi.com/history?country=%s&day=%s", country, day))
                .get()
                .addHeader("x-rapidapi-key", "aae654e3b4mshac93486a6d2cdacp1b0997jsn009b8d7955dd")
                .addHeader("x-rapidapi-host", "covid-193.p.rapidapi.com")
                .build();

        Country retVal;
        try {
            Response response = client.newCall(request).execute();
            retVal = ResponseToPojo.transformFromResponse(Objects.requireNonNull(response.body()).string());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
        return retVal;
    }
}
