package lt.viko.eif.savaitgalis.gpd.api;

import lt.viko.eif.savaitgalis.gpd.pojo.Country;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CovidApiHandler
 * @author Emilis Margevičius PI19B
 * @version 1.0
 * @since 1.0
 */
class CovidApiHandlerTest {

    /**
     * Test if API still works.
     */
    @Test
    void getCountryData() {
        Country country = CovidApiHandler.getCountryData("usa", "2020-06-01");
        //System.out.println(country);
        assertNotNull(country);
    }
}