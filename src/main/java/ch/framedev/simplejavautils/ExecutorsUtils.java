package ch.framedev.simplejavautils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("UnusedReturnValue")
public class ExecutorsUtils {

    private ScheduledExecutorService executorService;

    /**
     * Creates a new scheduled thread pool with the given number of threads.
     *
     * @param threads   Number of threads in the pool.
     * @param runnable  Task to execute.
     * @param delay     Initial delay before execution starts.
     * @param period    Interval between executions.
     * @param timeUnit  Time unit for delay and period.
     * @return A new ScheduledExecutorService.
     */
    public ScheduledExecutorService createScheduledThreadPool(int threads, Runnable runnable, int delay, int period, TimeUnit timeUnit) {
        shutdown(); // Ensures previous executor is stopped before creating a new one
        executorService = Executors.newScheduledThreadPool(threads);
        executorService.scheduleAtFixedRate(runnable, delay, period, timeUnit);
        return executorService;
    }

    /**
     * Creates a new single-threaded scheduled executor.
     *
     * @param runnable  Task to execute.
     * @param delay     Initial delay before execution starts.
     * @param period    Interval between executions.
     * @param timeUnit  Time unit for delay and period.
     * @return A new ScheduledExecutorService.
     */
    public ScheduledExecutorService createSingleThreadScheduledExecutor(Runnable runnable, int delay, int period, TimeUnit timeUnit) {
        shutdown(); // Ensures previous executor is stopped before creating a new one
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(runnable, delay, period, timeUnit);
        return executorService;
    }

    /**
     * Shuts down the executor service gracefully.
     */
    public void shutdown() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

    /**
     * Shuts down the executor service immediately.
     */
    public void shutdownNow() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
        }
    }

    /**
     * Checks if the executor service is running.
     *
     * @return true if running, false otherwise.
     */
    public boolean isRunning() {
        return executorService != null && !executorService.isShutdown();
    }
}