package CSC540.WolfWR.models;

/**
 * A restricted set of values representing the available membership levels
 * @author Brandon Jiang
 * @author Janelle Correia
 */
public enum MembershipLevel {
    GOLD,
    SILVER,
    PLATINUM;

    /**
     * Convert enum to a human legible string
     * @return a string with the name of the membership level
     */
    public String getName() {
        if (this.equals(GOLD))
            return "Gold";
        else if (this.equals(SILVER))
            return "Silver";
        else if (this.equals(PLATINUM))
            return "Platinum";
        return null;
    }

    /**
     * Converts human legible strings to enumberations
     * @param level the desired membership level as a string (case-insensitive)
     * @return the membership level as an enumeration / the format required for the program and DB
     */
    public static MembershipLevel getLevel(String level) {
        return switch (level.toLowerCase().trim()) {
            case "gold" -> GOLD;
            case "silver" -> SILVER;
            case "platinum" -> PLATINUM;
            default -> null;
        };
    }
}
