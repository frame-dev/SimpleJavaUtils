package ch.framedev.simplejavautils;

import org.junit.Test;

public class TimeTest {

    @Test
    public void testTimeConversion() {
        long milliseconds = 3661000; // 1 hour, 1 minute, and 1 second in milliseconds
        double seconds = Time.convert(milliseconds, Time.MILLISECONDS, Time.SECONDS);
        double minutes = Time.convert(milliseconds, Time.MILLISECONDS, Time.MINUTES);
        double hours = Time.convert(milliseconds, Time.MILLISECONDS, Time.HOURS);
        double days = Time.convert(milliseconds, Time.MILLISECONDS, Time.DAYS);
        double weeks = Time.convert(milliseconds, Time.MILLISECONDS, Time.WEEKS);
        double months = Time.convert(milliseconds, Time.MILLISECONDS, Time.MONTHS);
        double years = Time.convert(milliseconds, Time.MILLISECONDS, Time.YEARS);
        assert seconds == 3661.0;
        assert minutes == 61.016666666666666;
        assert hours == 1.0169444444444444;
        assert days == 0.04237268518518519;
        assert weeks == 0.006053240740740741;
        assert months == 0.0013930745814307458;
        assert years == 1.1608954845256215E-4;

        for(Time time : Time.values()) {
            double secondsPerUnit = time.getSecondsPerUnit();
            assert secondsPerUnit == Time.convert(1, time, Time.SECONDS);
        }

        double millisecondsLong = Time.MILLISECONDS.toMilliseconds(12L);
        assert millisecondsLong == 12.0;
        double secondsLong = Time.SECONDS.toSeconds(12L);
        assert secondsLong == 12.0;
        double minutesLong = Time.MINUTES.toMinutes(12L);
        assert minutesLong == 12.0;
        double hoursLong = Time.HOURS.toHours(12L);
        assert hoursLong == 12.0;
        double daysLong = Time.DAYS.toDays(12L);
        assert daysLong == 12.0;
        double weeksLong = Time.WEEKS.toWeeks(12L);
        assert weeksLong == 12.0;
        double monthsLong = Time.MONTHS.toMonths(12L);
        assert monthsLong == 12.0;
        double yearsLong = Time.YEARS.toYears(12L);
        assert yearsLong == 12.0;

        double secondsConvert = Time.convert(115L, Time.DAYS, Time.SECONDS);
        assert secondsConvert == 9936000.0;
    }
}
