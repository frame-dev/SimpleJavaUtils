package ch.framedev.simplejavautils;

public enum Length {

    METER(1),
    KILOMETER(1000),
    MILE(1609.344),
    YARD(0.9144),
    FOOT(0.3048),
    INCH(0.0254);

    final double value;

    Length(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
