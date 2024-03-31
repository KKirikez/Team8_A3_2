package model;

public class Boardgames extends Toy {
    private int minPlayers;
    private int maxPlayers;
    private String designers;

    /**
     * Constructs a Boardgames object with the specified attributes.
     *
     * @param serialNumber     the serial number of the board game
     * @param name             the name of the board game
     * @param brand            the brand of the board game
     * @param price            the price of the board game
     * @param availableCount   the number of available units of the board game
     * @param ageAppropriate   the age appropriateness of the board game
     * @param minPlayers       the minimum number of players for the board game
     * @param maxPlayers       the maximum number of players for the board game
     * @param designers        the designers of the board game
     */
    public Boardgames(String serialNumber, String name, String brand, float price, int availableCount, int ageAppropriate, int minPlayers, int maxPlayers, String designers) {
        super(serialNumber, name, brand, price, availableCount, maxPlayers);
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.designers = designers;
        this.setType("Board Game");
        this.setAgeAppropriate(ageAppropriate);
    }

    /**
     * Returns the minimum number of players for the board game.
     *
     * @return the minimum number of players
     */
    public int getMinPlayers() {
        return minPlayers;
    }

    /**
     * Sets the minimum number of players for the board game.
     *
     * @param minPlayers the minimum number of players
     */
    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    /**
     * Returns the maximum number of players for the board game.
     *
     * @return the maximum number of players
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * Sets the maximum number of players for the board game.
     *
     * @param maxPlayers the maximum number of players
     */
    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    /**
     * Returns the designers of the board game.
     *
     * @return the designers
     */
    public String getDesigners() {
        return designers;
    }

    /**
     * Sets the designers of the board game.
     *
     * @param designers the designers
     */
    public void setDesigners(String designers) {
        this.designers = designers;
    }

    /**
     * Checks if the given serial number is valid.
     *
     * @param serialNumber the serial number to check
     * @return true if the serial number starts with "7", "8", or "9", false otherwise
     */
    public boolean isSerialNumberValid(String serialNumber) {
        return serialNumber.startsWith("7") || serialNumber.startsWith("8") || serialNumber.startsWith("9");
    }

    /**
     * Returns a string representation of the board game.
     *
     * @return a string representation of the board game
     */
    @Override
    public String toString() {
        return super.toString() + 
                ", Players: " + getMinPlayers() + " - " + getMaxPlayers() +
                ", Designers: " + getDesigners();
    }

    /**
     * Returns a string representation of the board game for database storage.
     *
     * @return a string representation of the board game for database storage
     */
    public String toDatabase() {
        return getSerialNumber() + ";" +
                getName() + ";" +
                getBrand() + ";" +
                getPrice() + ";" +
                getAvailableCount() + ";" +
                getAgeAppropriate() + ";" +
                getMinPlayers() + "-" + getMaxPlayers() + ";" +
                getDesigners();
    }
}
