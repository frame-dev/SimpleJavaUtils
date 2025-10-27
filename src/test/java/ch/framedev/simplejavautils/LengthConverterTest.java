package ch.framedev.simplejavautils;

import org.junit.Test;

public class LengthConverterTest {

    @Test
    public void testLengthConversion() {
        LengthConverter converter = new LengthConverter(Length.METER, 1000);
        double kilometers = converter.convertTo(Length.KILOMETER);
        assert kilometers == 1.0 : "1000 meters should be 1 kilometer";

        double centimeters = converter.convertTo(Length.CENTIMETER);
        assert centimeters == 100000.0 : "1000 meters should be 100000 centimeters";

        double miles = converter.convertTo(Length.MILE);
        assert Math.abs(miles - 0.621371) < 0.0001 : "1000 meters should be approximately 0.621371 miles";
    }
}
