package lt.viko.eif.savaitgalis.gpd.pojo;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represents Cases object that is used by {@link Country}.
 *
 * It stores: new, active, critical, recovered and total cases.
 *
 * @author Emilis Margevičius PI19B
 * @version 1.0
 * @since 1.0
 * @see Country
 */

@Schema(title = "Cases", description = "Covid-19 cases counts")
public class Cases {

    @Schema(title = "New Cases", description = "Amount of new Covid-19 cases")
    private int newCases;
    @Schema(title = "Active Cases", description = "Amount of active Covid-19 cases")
    private int active;
    @Schema(title = "Critical Cases", description = "Amount of critical condition Covid-19 cases")
    private int critical;
    @Schema(title = "Recoveries", description = "Amount of recovered Covid-19 cases")
    private int recovered;
    @Schema(title = "Total cases", description = "Amount of total Covid-19 cases")
    private int total;

    /**
     * Empty constructor. Sets default values
     */
    public Cases() {
        this.newCases = 0;
        this.active = 0;
        this.critical = 0;
        this.recovered = 0;
        this.total = 0;
    }

    /**
     * Constructor with parameters.
     *
     * @param newCases Number of new cases
     * @param active Number of active cases
     * @param critical Number of critical cases
     * @param recovered Number of recoveries
     * @param total Total number of cases
     */
    public Cases(int newCases, int active, int critical, int recovered, int total) {
        this.newCases = newCases;
        this.active = active;
        this.critical = critical;
        this.recovered = recovered;
        this.total = total;
    }

    /**
     * Getter for new cases of Covid-19
     *
     * @return Integer of new cases
     */
    public int getNewCases() {
        return newCases;
    }

    /**
     * Setter for new cases pf Covid-19
     *
     * @param newCases number of new cases
     */
    public void setNewCases(int newCases) {
        this.newCases = newCases;
    }

    /**
     * Getter for active Covid-19 cases
     *
     * @return Integer number of cases
     */
    public int getActive() {
        return active;
    }

    /**
     * Setter for active Covid-19 cases
     *
     * @param active Covid-19 cases
     */
    public void setActive(int active) {
        this.active = active;
    }

    /**
     * Getter for the number of people in Critical condition caused by Covid-19
     *
     * @return Integer number of critical conditions
     */
    public int getCritical() {
        return critical;
    }

    /**
     * Setter for the number of people in Critical condition caused by Covid-19
     *
     * @param critical condition cases caused by Covid-19
     */
    public void setCritical(int critical) {
        this.critical = critical;
    }
    /**
     * Getter for Covid-19 recoveries.
     *
     * @return Integer number of recoveries from Covid-19
     */
    public int getRecovered() {
        return recovered;
    }

    /**
     * Setter for Covid-19 recoveries.
     *
     * @param recovered number of recoveries
     */
    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    /**
     * Getter for total Covid-19 cases
     *
     * @return an integer number of total cases
     */
    public int getTotal() {
        return total;
    }

    /**
     * Getter for total Covid-19 cases
     *
     * @param total number of cases
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * Override for toString() method from {@link java.lang.Object}
     *
     * @return a string representation of the object.
     * @see java.lang.Object
     */
    @Override
    public String toString() {
        return String.format("\tCases:\n" +
                "\t\tNew Cases:\t%d\n" +
                "\t\tActive Cases: \t%d\n" +
                "\t\tCritical Cases: %d\n" +
                "\t\tRecovery Cases:\t%d\n" +
                "\t\tTotal Cases:\t%d", newCases, active, critical, recovered, total);
    }
}
