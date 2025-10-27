package ch.framedev.simplejavautils;

import org.junit.Test;

public class LengthTest {

    @Test
    public void testLengthEnumConversion() {
        double metersInKilometer = Length.KILOMETER.convertTo(1, Length.METER);
        assert metersInKilometer == 1000.0 : "1 kilometer should be 1000 meters";

        double inchesInMeter = Length.METER.convertTo(1, Length.INCH);
        assert Math.abs(inchesInMeter - 39.3701) < 0.0001 : "1 meter should be approximately 39.3701 inches";

        double milesInKilometer = Length.KILOMETER.convertTo(1, Length.MILE);
        assert Math.abs(milesInKilometer - 0.621371) < 0.0001 : "1 kilometer should be approximately 0.621371 miles";
    }
}
