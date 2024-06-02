package ch.framedev.simplejavautils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorsUtils {

    private ScheduledExecutorService executorService;

    public ScheduledExecutorService getExecutorService() {
        return executorService;
    }

    public ExecutorsUtils createNewScheduledThreadPool(int thread, Runnable runnable, int delay, int period, TimeUnit timeUnit) {
        executorService = Executors.newScheduledThreadPool(thread);
        executorService.scheduleAtFixedRate(runnable, delay, period, timeUnit);
        return this;
    }

    public ExecutorsUtils createNewSingleThreadScheduledExecutor(Runnable runnable, int delay, int period, TimeUnit timeUnit) {
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(runnable, delay, period, timeUnit);
        return this;
    }
}
