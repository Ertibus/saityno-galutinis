package lt.viko.eif.savaitgalis.gpd.repos;

import lt.viko.eif.savaitgalis.gpd.pojo.Country;
import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Emilis Margevičius PI19B
 * @version 1.0
 * @since 1.0
 */
class CovidApiRepositoryTest {

    /**
     * Check if database exist and connection to it is successful
     */
    @Test
    void connectToDb() {
        try {
            DriverManager.getConnection(CovidApiRepository.DB_URL).close();
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Failed to connect to the database");
        }

    }
    /**
     * Test if caching is successful
     */
    @Test
    void testCaching(){
        CovidApiRepository repo = new CovidApiRepository();
        Country cache = repo.getCountry("usa", "2020-06-06");
        assertNotNull(cache);
        assertTrue(repo.countryIsCached("usa", "2020-06-06"));
    }

    @Test
    void testToday(){
        CovidApiRepository repo = new CovidApiRepository();
        Country cache = repo.getCountry("usa", "today");
        assertNotNull(cache);
    }


}