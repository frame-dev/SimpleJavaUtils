package ch.framedev.simplejavautils;

public class LengthConverter {

    private final Length length;
    private final double amount;

    public LengthConverter(Length length, double amount) {
        this.length = length;
        this.amount = amount;
    }

    public double toMeters() {
        return amount * length.getValue();
    }

    public double toMiles() {
        return amount / Length.MILE.getValue();
    }

    public double toKilometers() {
        if (length == Length.METER)
            return amount / Length.KILOMETER.getValue();
        return amount * length.getValue() / Length.METER.getValue();
    }

    public double toYards() {
        return amount * length.getValue() / Length.YARD.getValue();
    }

    public double toFeet() {
        return amount * length.getValue() / Length.FOOT.getValue();
    }

    public double toInches() {
        return amount * length.getValue() / Length.INCH.getValue();
    }
}
