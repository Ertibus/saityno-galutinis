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

    /**
     * Test for the Getter and Setter of 'country' field
     */
    @Test
    void getterSetterCountry() {
        Country country = new Country();
        String value = "77777";
        country.setCountry(value);
        assertEquals("77777", country.getCountry());
    }
    /**
     * Test for the Getter and Setter of 'date' field
     */
    @Test
    void getterSetterDate() {
        Country country = new Country();
        int value = 77777;
        country.setDate(value);
        assertEquals(77777, country.getDate());
    }
    /**
     * Test for the Getter and Setter of 'tests' field
     */
    @Test
    void getterSetterTests() {
        Country country = new Country();
        int value = 77777;
        country.setTests(value);
        assertEquals(77777, country.getTests());
    }
    /**
     * Test for the Getter and Setter of {@link Cases} field
     * @see Cases
     */
    @Test
    void getterSetterCases() {
        Country country = new Country();
        country.setCases(new Cases());
        assertEquals(new Cases().toString(), country.getCases().toString());
    }

    /**
     * Test for the Getter and Setter of {@link Deaths} field
     * @see Deaths
     */
    @Test
    void getterSetterDeaths() {
        Country country = new Country();
        country.setDeaths(new Deaths());
        assertEquals(new Deaths().toString(), country.getDeaths().toString());
    }

    /**
     * Test that simply prints the structure of {@link Country}
     * @see Country
     */
    @Test
    void basicPojoStruct(){
        try {
            Country country = new Country();
            System.out.println(country);
        } catch (Exception ex) {
            fail("For some reason this test failed :/\n" + ex.getMessage());
        }
    }
}