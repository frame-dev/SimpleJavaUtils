package ch.framedev.simplejavautils;

import org.junit.Test;

public class ExecutorsUtilsTest {

    @Test
    public void testCreateScheduledThreadPool() {
        ExecutorsUtils executorsUtils = new ExecutorsUtils();
        Runnable task = () -> System.out.println("Task executed");
        executorsUtils.createScheduledThreadPool(2, task, 1, 1, java.util.concurrent.TimeUnit.SECONDS);
        // Add assertions or wait to observe the scheduled task execution
        executorsUtils.shutdown();
    }

    @Test
    public void testCreateSingleThreadScheduledExecutor() {
        ExecutorsUtils executorsUtils = new ExecutorsUtils();
        Runnable task = () -> System.out.println("Single thread task executed");
        executorsUtils.createSingleThreadScheduledExecutor(task, 1, 1, java.util.concurrent.TimeUnit.SECONDS);
        // Add assertions or wait to observe the scheduled task execution
        executorsUtils.shutdown();
    }

    @Test
    public void testShutdownNow() {
        ExecutorsUtils executorsUtils = new ExecutorsUtils();
        Runnable task = () -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("Task interrupted");
            }
        };
        executorsUtils.createScheduledThreadPool(1, task, 0, 1, java.util.concurrent.TimeUnit.SECONDS);
        executorsUtils.shutdownNow();
        // Add assertions to verify the executor is shut down immediately
    }

    @Test
    public void testShutdown() {
        ExecutorsUtils executorsUtils = new ExecutorsUtils();
        Runnable task = () -> System.out.println("Task executed before shutdown");
        executorsUtils.createScheduledThreadPool(1, task, 0, 1, java.util.concurrent.TimeUnit.SECONDS);
        executorsUtils.shutdown();
        // Add assertions to verify the executor is shut down gracefully
    }

    @Test
    public void testMultipleExecutors() {
        ExecutorsUtils executorsUtils = new ExecutorsUtils();
        Runnable task1 = () -> System.out.println("Task 1 executed");
        Runnable task2 = () -> System.out.println("Task 2 executed");

        executorsUtils.createScheduledThreadPool(1, task1, 0, 1, java.util.concurrent.TimeUnit.SECONDS);
        executorsUtils.createSingleThreadScheduledExecutor(task2, 0, 1, java.util.concurrent.TimeUnit.SECONDS);

        // Add assertions or wait to observe the scheduled task execution
        executorsUtils.shutdown();
    }

    @Test
    public void isRunning() {
        ExecutorsUtils executorsUtils = new ExecutorsUtils();
        Runnable task = () -> System.out.println("Task executed");
        executorsUtils.createScheduledThreadPool(1, task, 0, 1, java.util.concurrent.TimeUnit.SECONDS);
        assert executorsUtils.isRunning() : "Executor should be running";
        executorsUtils.shutdown();
        assert !executorsUtils.isRunning() : "Executor should be shut down";
    }
}
