//I worked on the homework assignment alone, using only course materials.

/**
 * Creates move objects
 *
 * @author Eashan Gandotra
 * @version 1.0
 */
public class Move {
    private String name;
    private int power;
    private String type;

    /**
     * Creates move objects
     *
     * @param n name
     * @param p power
     * @param t type
     */
    public Move(String n, int p, String t) {
        name = n;
        power = p;
        type = t;

    }

    /**
     * Getter method for name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method for power
     *
     * @return the power
     */
    public int getPower() {
        return power;
    }

    /**
     * Getter method for type
     *
     * @return type of pokemon
     */
    public String getType() {
        return type;
    }
}
