package ch.framedev.simplejavautils;

public class LengthConverter {

    private final Length length;
    private final double amount;

    public LengthConverter(Length length, double amount) {
        this.length = length;
        this.amount = amount;
    }

    /**
     * Converts the stored length to another unit.
     *
     * @param to The target unit.
     * @return The converted value.
     */
    public double convertTo(Length to) {
        return (amount * length.getValue()) / to.getValue();
    }

    @Override
    public String toString() {
        return amount + " " + length.name();
    }
}