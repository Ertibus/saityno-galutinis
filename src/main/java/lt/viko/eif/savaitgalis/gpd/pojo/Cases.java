package lt.viko.eif.savaitgalis.gpd.pojo;

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
public class Cases {
    private int newCases = 0;
    private int active = 0;
    private int critical = 0;
    private int recovered = 0;
    private int total = 0;

    /**
     * Getter for new cases of Covid-19
     * @return Integer of new cases
     */
    public int getNewCases() {
        return newCases;
    }

    /**
     * Setter for new cases pf Covid-19
     * @param newCases number of new cases
     */
    public void setNewCases(int newCases) {
        this.newCases = newCases;
    }

    /**
     * Getter for active Covid-19 cases
     * @return Integer number of cases
     */
    public int getActive() {
        return active;
    }

    /**
     * Setter for active Covid-19 cases
     * @param active Covid-19 cases
     */
    public void setActive(int active) {
        this.active = active;
    }

    /**
     * Getter for the number of people in Critical condition caused by Covid-19
     * @return Integer number of critical conditions
     */
    public int getCritical() {
        return critical;
    }

    /**
     * Setter for the number of people in Critical condition caused by Covid-19
     * @param critical condition cases caused by Covid-19
     */
    public void setCritical(int critical) {
        this.critical = critical;
    }
    /**
     * Getter for Covid-19 recoveries.
     * @return Integer number of recoveries from Covid-19
     */
    public int getRecovered() {
        return recovered;
    }

    /**
     * Setter for Covid-19 recoveries.
     * @param recovered number of recoveries
     */
    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    /**
     * Getter for total Covid-19 cases
     * @return an integer number of total cases
     */
    public int getTotal() {
        return total;
    }

    /**
     * Getter for total Covid-19 cases
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
