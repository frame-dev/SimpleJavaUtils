package ch.framedev.simplejavautils;

/**
 * / This Plugin was Created by FrameDev
 * / Package : ch.framedev.simplejavautils
 * / ClassName CooldownMilliSeconds
 * / Date: 05.03.22
 * / Project: JavaUtils
 * / Copyrighted by FrameDev
 */

public class CooldownMilliSeconds extends Cooldown {
    private final int id;
    private final long milliSecs;
    private long millisecondsLeft;
    private long milliSeconds;
    private final long actualTime;

    public CooldownMilliSeconds(int id, long milliSecs, long actualTime) {
        super();
        this.id = id;
        this.milliSecs = milliSecs;
        this.actualTime = actualTime;
    }

    public CooldownMilliSeconds(int id, long milliSecs) {
        super();
        this.id = id;
        this.milliSecs = milliSecs;
        this.actualTime = System.currentTimeMillis();
    }

    public long getMilliSecondsLeft() {
        return millisecondsLeft;
    }

    public long getMilliSecs() {
        return milliSecs;
    }

    public long getMilliSeconds() {
        return milliSeconds;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean check() {
        millisecondsLeft = (actualTime + milliSecs) - System.currentTimeMillis();
        milliSeconds = actualTime + milliSecs - System.currentTimeMillis();
        return millisecondsLeft <= 0;
    }

    @Override
    public void sendInformation() {
        new SimpleJavaUtils().getLogger().info("ID : " + id);
        new SimpleJavaUtils().getLogger().info("MilliSeconds Left : " + millisecondsLeft);
        new SimpleJavaUtils().getLogger().info("Is Expired : " + isExpired());
    }
}