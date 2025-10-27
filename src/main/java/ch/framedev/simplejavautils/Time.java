// java
package ch.framedev.simplejavautils;

public enum Time {

    MILLISECONDS(0.001),
    SECONDS(1.0),
    MINUTES(60.0),
    HOURS(3600.0),
    DAYS(86400.0),
    WEEKS(604800.0),
    MONTHS(2628000.0),  // Approximate (30.44 days)
    YEARS(31536000.0);  // Approximate (365.25 days)

    private final double secondsPerUnit;

    Time(double secondsPerUnit) {
        this.secondsPerUnit = secondsPerUnit;
    }

    public double getSecondsPerUnit() {
        return secondsPerUnit;
    }

    /**
     * Generic conversion between units (fractional values supported).
     */
    public static double convert(double value, Time from, Time to) {
        return value * from.secondsPerUnit / to.secondsPerUnit;
    }

    /**
     * Backwards-compatible overload for integral values.
     */
    public static double convert(long value, Time from, Time to) {
        return convert((double) value, from, to);
    }

    /**
     * Convert a value in this unit to the target unit.
     */
    public double convertTo(double value, Time to) {
        return convert(value, this, to);
    }

    public double convertTo(long value, Time to) {
        return convert((double) value, this, to);
    }

    /* Convenience helpers */

    public double toMilliseconds(double value) {
        return convertTo(value, MILLISECONDS);
    }

    public double toMilliseconds(long value) {
        return toMilliseconds((double) value);
    }

    public double toSeconds(double value) {
        return convertTo(value, SECONDS);
    }

    public double toSeconds(long value) {
        return toSeconds((double) value);
    }

    public double toMinutes(double value) {
        return convertTo(value, MINUTES);
    }

    public double toMinutes(long value) {
        return toMinutes((double) value);
    }

    public double toHours(double value) {
        return convertTo(value, HOURS);
    }

    public double toHours(long value) {
        return toHours((double) value);
    }

    public double toDays(double value) {
        return convertTo(value, DAYS);
    }

    public double toDays(long value) {
        return toDays((double) value);
    }

    public double toWeeks(double value) {
        return convertTo(value, WEEKS);
    }

    public double toWeeks(long value) {
        return toWeeks((double) value);
    }

    public double toMonths(double value) {
        return convertTo(value, MONTHS);
    }

    public double toMonths(long value) {
        return toMonths((double) value);
    }

    public double toYears(double value) {
        return convertTo(value, YEARS);
    }

    public double toYears(long value) {
        return toYears((double) value);
    }

    @Override
    public String toString() {
        return this.name() + " (" + secondsPerUnit + " seconds)";
    }
}