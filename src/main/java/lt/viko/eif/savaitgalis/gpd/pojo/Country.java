package lt.viko.eif.savaitgalis.gpd.pojo;

/**
 * Represents Country object that we use to store data from COVID API
 * It's also used to cache the response.
 *
 * It stores: country name, date, test count,{@link Cases} and {@link Deaths} fields.
 *
 * @author Emilis Margevičius PI19B
 * @version 1.0
 * @since 1.0
 * @see Cases
 * @see Deaths
 */
public class Country {
    private String country;
    private int date;
    private int tests;
    private Cases cases;
    private Deaths deaths;

    /**
     * Empty constructor. Sets default values.
     */
    public Country() {
        this.country = "country";
        this.date = 19991225;
        this.tests = 0;
        this.cases  = new Cases();
        this.deaths = new Deaths();
    }

    /**
     * Constructor with parameters.
     * @param country Name of the country
     * @param date Date the data represents
     * @param tests Total number of tests
     * @param cases {@link Cases} object
     * @param deaths {@link Deaths} object
     * @see Cases
     * @see Deaths
     */
    public Country(String country, int date, int tests, Cases cases, Deaths deaths) {
        this.country = country;
        this.date = date;
        this.tests = tests;
        this.cases = cases;
        this.deaths = deaths;
    }

    /**
     * Getter for the country name
     * @return a string name of the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Setter for the country name
     * @param country the name of the country
     */
    public void setCountry(String country) {
        this.country = country;
    }



    /**
     * Getter for the date, the data represents.
     * @return a string date of data
     */
    public int getDate() {
        return date;
    }
    /**
     * Setter for the date, the data represents.
     * @param date of data
     */
    public void setDate(int date) {
        this.date = date;
    }

    /**
     * Getter for the number of tests for Covid-19
     * @return an integer number of tests cases
     */
    public int getTests() {
        return tests;
    }

    /**
     *  Setter for the number of tests for Covid-19
     * @param tests number of tests cases
     */
    public void setTests(int tests) {
        this.tests = tests;
    }

    /**
     * Getter for the stored {@link Cases} object
     * @return a {@link Cases} object
     * @see Cases
     */
    public Cases getCases() {
        return cases;
    }

    /**
     * Setter for the stored {@link Cases} object
     * @param cases a new Cases object
     * @see Cases
     */
    public void setCases(Cases cases) {
        this.cases = cases;
    }

    /**
     * Getter for the stored {@link Deaths} object
     * @return the {@link Deaths} object
     * @see Deaths
     */
    public Deaths getDeaths() {
        return deaths;
    }

    /**
     * Setter for the stored {@link Deaths} object
     * @param deaths {@link Deaths} object
     * @see Deaths
     */
    public void setDeaths(Deaths deaths) {
        this.deaths = deaths;
    }

    /**
     * Override for toString() method from {@link java.lang.Object}
     *
     * @return a string representation of the object.
     * @see java.lang.Object
     */
    @Override
    public String toString() {
        return String.format("\tCountry:\t\t%s\n" +
                "\tDate:\t\t\t%s\n" +
                "\tTest Count: \t\t%d\n" +
                "%s\n" +
                "%s\n", country, date, tests, cases.toString(), deaths.toString());
    }
}
