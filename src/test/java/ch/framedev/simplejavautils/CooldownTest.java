package ch.framedev.simplejavautils;

import org.junit.Test;

public class CooldownTest {

    @Test
    public void testCooldownCreation() {
        Cooldown cooldown = new Cooldown(1, 10);
        assert cooldown.getId() == 1;
        assert cooldown.getSeconds() == 10;
    }

    @Test
    public void testCooldownWithActualTime() {
        long currentTime = System.currentTimeMillis();
        Cooldown cooldown = new Cooldown(2, 20, currentTime);
        assert cooldown.getId() == 2;
        assert cooldown.getSeconds() == 20;
    }

    @Test
    public void testDefaultCooldown() {
        Cooldown cooldown = new Cooldown();
        assert cooldown.getId() == 0;
        assert cooldown.getSeconds() == 0;
    }

    @Test
    public void testGetters() {
        Cooldown cooldown = new Cooldown(3, 30);
        assert cooldown.getId() == 3;
        assert cooldown.getSeconds() == 30;
        assert cooldown.getSecondsLeft() == 0; // Assuming no time has passed
        assert cooldown.getMilliSecondsLeft() == 0; // Assuming no time has passed
        assert cooldown.getMilliSeconds() == 0; // Assuming no time has passed
    }

    @Test
    public void testCooldownSerialization() {
        Cooldown cooldown = new Cooldown(4, 40);
        SimpleJavaUtils utils = new SimpleJavaUtils();
        String base64 = utils.objectToBase64(cooldown);
        Cooldown deserializedCooldown = utils.objectFromBase64(base64);
        assert deserializedCooldown.getId() == 4;
        assert deserializedCooldown.getSeconds() == 40;
    }

    @Test
    public void testCooldownCheckAndExpiration() throws InterruptedException {
        Cooldown cooldown = new Cooldown(5, 1); // 1 second cooldown
        assert !cooldown.isExpired();
        Thread.sleep(1500); // Wait for 1.5 seconds
        assert cooldown.isExpired();
    }

    @Test
    public void testSendInformation() {
        Cooldown cooldown = new Cooldown(6, 60);
        cooldown.sendInformation(); // Just ensure no exceptions are thrown
    }
}
