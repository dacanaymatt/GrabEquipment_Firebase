package Model;

/**
 * Created by Matthew on 4/3/2017.
 */
public class Equipment {

    public Equipment(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public Equipment() {

    }

    private String name;
    private int amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
