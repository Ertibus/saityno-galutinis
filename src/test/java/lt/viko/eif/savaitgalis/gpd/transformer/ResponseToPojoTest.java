package lt.viko.eif.savaitgalis.gpd.transformer;

import lt.viko.eif.savaitgalis.gpd.pojo.Cases;
import lt.viko.eif.savaitgalis.gpd.pojo.Country;
import lt.viko.eif.savaitgalis.gpd.pojo.Deaths;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link ResponseToPojo}
 * @author Emilis Margevičius PI19B
 * @version 1.0
 * @since 1.0
 * @see ResponseToPojo
 */
class ResponseToPojoTest {

    /**
     * Tests the 'transformFromResponse' function.
     */
    @Test
    void transformFromResponse() {
        String exampleResponse = "{\"get\":\"statistics\",\"parameters\":{\"country\":\"Lithuania\"},\"errors\":[],\"results\":1,\"response\":[{\"continent\":\"Europe\",\"country\":\"Lithuania\",\"population\":2687738,\"cases\":{\"new\":\"+503\",\"active\":15299,\"critical\":147,\"recovered\":254322,\"1M_pop\":\"101895\",\"total\":273866},\"deaths\":{\"new\":\"+5\",\"1M_pop\":\"1579\",\"total\":4245},\"tests\":{\"1M_pop\":\"1244989\",\"total\":3346203},\"day\":\"2021-05-29\",\"time\":\"2021-05-29T12:00:03+00:00\"}]}\n";

        Cases cases = new Cases(503, 15299, 147, 254322, 273866);
        Deaths deaths = new Deaths(5, 4245);
        Country country = new Country("Lithuania", "2021-05-29", 3346203, cases, deaths);

        Country pojoCountry = null;
        try {
            pojoCountry = ResponseToPojo.transformFromResponse(exampleResponse);
        } catch (ParseException e) {
            fail(e.getMessage());
        }

        assertEquals(country.toString(), pojoCountry.toString());
    }
}