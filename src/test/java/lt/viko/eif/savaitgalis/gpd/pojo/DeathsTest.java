package lt.viko.eif.savaitgalis.gpd.pojo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link Deaths}
 * @author Emilis Margevičius PI19B
 * @version 1.0
 * @since 1.0
 * @see Deaths
 */
class DeathsTest {

    @Test
    void getterSetterNewDeaths() {
        Deaths deaths = new Deaths();
        int value = 77777;
        deaths.setNewDeaths(value);
        assertEquals(77777, deaths.getNewDeaths());
    }

    @Test
    void getterSetterTotalDeaths() {
        Deaths deaths = new Deaths();
        int value = 77777;
        deaths.setTotalDeaths(value);
        assertEquals(77777, deaths.getTotalDeaths());
    }
}