package Beans;

/**
 * Enum of initial categories created with the DB
 */
public enum InitialCategories {
    OTHER(new StringBuffer("OTHER"), 1),
    FOOD(new StringBuffer("FOOD"), 2),
    ELECTRICITY(new StringBuffer("ELECTRICITY"), 3),
    RESTAURANT(new StringBuffer("RESTAURANT"), 4),
    VACATION(new StringBuffer("VACATION"), 5);
    private StringBuffer text;
    private int number;

    InitialCategories(StringBuffer text, int number) {
        this.text = text;
        this.number = number;
    }

    public StringBuffer getText() {
        return text;
    }

    public int getNumber() {
        return number;
    }
}
