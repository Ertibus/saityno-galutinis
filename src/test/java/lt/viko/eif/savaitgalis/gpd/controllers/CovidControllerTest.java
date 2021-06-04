package lt.viko.eif.savaitgalis.gpd.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sqlite.SQLiteException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test CovidController
 *
 * @author Emilis Margevičius PI19B
 * @version 1.0
 * @since 1.0
 */
class CovidControllerTest {
    private static CovidController controller;

    /**
     * Initialize controller
     */
    @BeforeAll
    static void controllerInstance(){
            controller = new CovidController();
    }

    /**
     * Test getting favorites list
     */
    @Test
    void allFavCountry() {
        try {
            controller.allFavCountry();
        } catch (Exception exception) {
            fail(exception);
        }
    }
}