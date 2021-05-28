package lt.viko.eif.savaitgalis.gpd.pojo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link Country}
 *
 * @author Emilis Margevičius PI19B
 * @version 1.0
 * @since 1.0
 * @see Country
 * @see Cases
 * @see Deaths
 */
class CountryTest {

    @Test
    void getterSetterCountry() {
        Country country = new Country();
        String value = "77777";
        country.setCountry(value);
        assertEquals("77777", country.getCountry());
    }

    @Test
    void getterSetterDate() {
        Country country = new Country();
        String value = "77777";
        country.setDate(value);
        assertEquals("77777", country.getDate());
    }

    @Test
    void getterSetterTests() {
        Country country = new Country();
        int value = 77777;
        country.setTests(value);
        assertEquals(77777, country.getTests());
    }

    @Test
    void getterSetterCases() {
        Country country = new Country();
        country.setCases(new Cases());
        assertEquals(new Cases().toString(), country.getCases().toString());
    }

    @Test
    void getterSetterDeaths() {
        Country country = new Country();
        country.setDeaths(new Deaths());
        assertEquals(new Deaths().toString(), country.getDeaths().toString());
    }

    @Test
    void basicPojoStruct(){
        try {
            Country country = new Country();
            System.out.println(country.toString());
        } catch (Exception ex) {
            fail("For some reason this test failed :/\n" + ex.getMessage());
        }

    }
}