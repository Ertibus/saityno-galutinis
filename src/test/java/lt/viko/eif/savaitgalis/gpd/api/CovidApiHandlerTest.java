package lt.viko.eif.savaitgalis.gpd.api;

import lt.viko.eif.savaitgalis.gpd.pojo.Country;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CovidApiHandler
 *
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

    @Test
    void checkNotExisting() {
        Country country = CovidApiHandler.getCountryData("NOT_EXISTING COUNTRY", "2020-06-01");
        //System.out.println(country);
        assertNull(country);
    }

    @Test
    void checkBadInput() {
        Country country = CovidApiHandler.getCountryData("usa", "2222222");
        //System.out.println(country);
        assertNull(country);
    }

    @Test
    void checkFuture() {
        Country country = CovidApiHandler.getCountryData("usa", "3020-06-01");
        //System.out.println(country);
        assertNull(country);
    }
}