package lt.viko.eif.savaitgalis.gpd.pojo;

/**
 * Represents Cases object that is used by {@link Country}
 *
 * It stores: new and total cases.
 *
 * @author Emilis Margevičius PI19B
 * @version 1.0
 * @since 1.0
 * @see Country
 */
public class Deaths {

    private int newDeaths;
    private int total;

    /**
     * Empty constructor. Sets default values
     */
    public Deaths(){
        this.newDeaths = 0;
        this.total = 0;
    }

    /**
     * Constructor with parameters
     * @param newDeaths number of new deaths
     * @param total number of total deaths
     */
    public Deaths(int newDeaths, int total) {
        this.newDeaths = newDeaths;
        this.total = total;
    }

    /**
     * Getter for the number of new deaths caused by Covid-19
     * @return an integer of new deaths caused by Covid-19
     */
    public int getNewDeaths() {
        return newDeaths;
    }
    /**
     * Getter for the number of new deaths caused by Covid-19
     * @param newDeaths new deaths caused by Covid-19
     */
    public void setNewDeaths(int newDeaths) {
        this.newDeaths = newDeaths;
    }

    /**
     * Getter for the number of total deaths caused by Covid-19
     * @return an integer of total deaths caused by Covid-19
     */
    public int getTotalDeaths() {
        return total;
    }
    /**
     * Getter for the number of total deaths caused by Covid-19
     * @param deaths total deaths caused by Covid-19
     */
    public void setTotalDeaths(int deaths) {
        this.total = deaths;
    }

    /**
     * Override for toString() method from {@link java.lang.Object}
     *
     * @return a string representation of the object.
     * @see java.lang.Object
     */
    @Override
    public String toString() {
        return String.format("\tDeaths:\n" +
                "\t\tNew Cases:\t%s\n" +
                "\t\tTotal Cases:\t%s\n", newDeaths, total);
    }
}
