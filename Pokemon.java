/**
 * Creates Pokemon objects
 *
 * @author Eashan Gandotra
 * @version 1.0
 */
public class Pokemon {
    private String name;
    private int level;
    private double maxHP;
    private double currentHP;
    private int atk;
    private Move[] moves;
    private String type;
    private boolean fainted;

    /**
     * Constructor to initialize Pokemon objects
     *
     * @param n    names
     * @param l    level
     * @param h    health
     * @param a    attack stat
     * @param t    type
     * @param move array of moves
     */
    public Pokemon(String n, int l, double h, int a, String t, Move... move) {
        name = n;
        level = l;
        maxHP = h;
        atk = a;
        type = t;
        currentHP = maxHP;
        moves = move;
    }

    /**
     * No args constructor for Pokemon objects
     */
    public Pokemon() {
    }

    /**
     * Getter method for name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter mathod for level
     *
     * @return level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Getter method for the atk stat
     *
     * @return atk stat
     */
    public int getAtk() {
        return atk;
    }

    /**
     * Getter method for maxHP
     *
     * @return maxHP
     */
    public double getMaxHP() {
        return maxHP;
    }

    /**
     * Getter method for type
     *
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Getter method for currentHP
     *
     * @return currentHP
     */
    public double getCurrentHP() {
        return currentHP;
    }

    /**
     * Getter method for the moves
     *
     * @return moves
     */
    public Move[] getMoves() {
        return moves;
    }

    /**
     * Sets the currentHP to s specified double
     *
     * @param currentHP the current HP
     */
    public void setCurrentHP(double currentHP) {
        this.currentHP = currentHP;
        if (this.currentHP <= 0) {
            fainted = true;
        }
    }

    /**
     * Sets the stats of the Pokemon to those of another specified Pokemon
     *
     * @param p a Pokemon
     */
    public void setStats(Pokemon p) {
        this.maxHP = p.getMaxHP();
        this.level = p.getLevel();
        this.atk = p.getAtk();
        this.currentHP = p.getCurrentHP();
        this.name = p.name;
        this.type = p.type;
        this.moves = p.moves;
    }

    /**
     * Setter for the Pokemon name
     *
     * @param name Pokemon name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gives the multiplier by which the power of an attack is multiplied based on type advantage
     *
     * @param move a Move being used on the Pokemon
     * @return the multiplier value
     */
    public double compareType(Move move) {
        if (move.getType().equals(type)) {
            return 0.5;
        } else if (move.getType().equals("WATER")) {
            if (type.equals("FIRE")) {
                return 2.0;
            } else if (type.equals("GRASS")) {
                return 0.5;
            }
        } else if (move.getType().equals("GRASS")) {
            if (type.equals("WATER")) {
                return 2.0;
            } else if (type.equals("FIRE")) {
                return 0.5;
            } else if (type.equals("FLYING")) {
                return 0.5;
            }
        } else if (move.getType().equals("FIRE")) {
            if (type.equals("GRASS")) {
                return 2.0;
            } else if (type.equals("WATER")) {
                return 0.5;
            }
        } else if (move.getType().equals("FLYING")) {
            if (type.equals("GRASS")) {
                return 2.0;
            }
        }
        return 1;
    }

    /**
     * Returns the fainted status of the Pokemon
     *
     * @return true if the Pokemon fainted; false otherwise
     */
    public boolean isFainted() {
        return fainted;
    }

    /**
     * Sets the status of fainted
     *
     * @param fainted the fainted status of the Pokemon
     */
    public void setFainted(boolean fainted) {
        this.fainted = fainted;
    }
}
