package ch.framedev.simplejavautils;

public enum Length {

    MILLIMETER(0.001),
    CENTIMETER(0.01),
    METER(1),
    KILOMETER(1000),
    INCH(0.0254),
    FOOT(0.3048),
    YARD(0.9144),
    MILE(1609.344),
    LIGHT_YEAR(9.461e15); // Approximate distance of a light-year in meters

    private final double toMeters;

    Length(double toMeters) {
        this.toMeters = toMeters;
    }

    public double getValue() {
        return toMeters;
    }

    /**
     * Converts a given value from this unit to another unit.
     *
     * @param value The amount in this unit.
     * @param to    The target unit.
     * @return The converted value in the target unit.
     */
    public double convertTo(double value, Length to) {
        return (value * this.toMeters) / to.toMeters;
    }

    @Override
    public String toString() {
        return name() + " (" + toMeters + " meters)";
    }
}