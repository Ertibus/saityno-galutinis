package lt.viko.eif.savaitgalis.gpd.pojo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link Cases}
 * @author Emilis Margevičius PI19B
 * @version 1.0
 * @since 1.0
 * @see Cases
 */
class CasesTest {

    /**
     * Test for the Getter and Setter of 'newCases' field
     */
    @Test
    void getterSetterNewCases() {
        Cases cases = new Cases();
        int value = 77777;
        cases.setNewCases(value);
        assertEquals(77777, cases.getNewCases());
    }

    /**
     * Test for the Getter and Setter of 'active' field
     */
    @Test
    void getterSetterActive() {
        Cases cases = new Cases();
        int value = 77777;
        cases.setActive(value);
        assertEquals(77777, cases.getActive());
    }

    /**
     * Test for the Getter and Setter of 'critical' field
     */
    @Test
    void getterSetterCritical() {
        Cases cases = new Cases();
        int value = 77777;
        cases.setCritical(value);
        assertEquals(77777, cases.getCritical());
    }

    /**
     * Test for the Getter and Setter of 'recovered' field
     */
    @Test
    void getterSetterRecovered() {
        Cases cases = new Cases();
        int value = 77777;
        cases.setRecovered(value);
        assertEquals(77777, cases.getRecovered());
    }

    /**
     * Test for the Getter and Setter of 'total' field
     */
    @Test
    void getterSetterTotal() {
        Cases cases = new Cases();
        int value = 77777;
        cases.setTotal(value);
        assertEquals(77777, cases.getTotal());
    }
}