package ch.framedev.simplejavautils;

import org.junit.Test;

public class CooldownMillisecondsTest {

    @Test
    public void testCooldownMillisecondsCreation() {
        long currentTime = System.currentTimeMillis();
        CooldownMilliSeconds cooldown = new CooldownMilliSeconds(1, 5000, currentTime);
        assert cooldown.getId() == 1;
        assert cooldown.getMilliSecs() == 5000;
    }

    @Test
    public void testGetters() {
        CooldownMilliSeconds cooldown = new CooldownMilliSeconds(2, 3000);
        assert cooldown.getId() == 2;
        assert cooldown.getMilliSecs() == 3000;
        assert cooldown.getMilliSecondsLeft() == 0; // Assuming no time has passed
        assert cooldown.getMilliSeconds() == 0; // Assuming no time has passed
    }

    @SuppressWarnings("AssertWithSideEffects")
    @Test
    public void testCooldownMillisecondsCheckAndExpiration() throws InterruptedException {
        CooldownMilliSeconds cooldown = new CooldownMilliSeconds(3, 1000); // 1 second cooldown
        assert !cooldown.check();
        Thread.sleep(1500); // Wait for 1.5 seconds
        assert cooldown.check();
    }

    @Test
    public void testSendInformation() {
        CooldownMilliSeconds cooldown = new CooldownMilliSeconds(4, 60000);
        cooldown.sendInformation(); // Just ensure no exceptions are thrown
    }
}
