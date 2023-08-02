package com.asset.loggingService.factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomThreadFactory implements ThreadFactory {
    private static  AtomicInteger poolNumber = new AtomicInteger(1);
    private ThreadGroup group;
    private AtomicInteger threadNumber = new AtomicInteger(1);
    private String namePrefix;

    public CustomThreadFactory(String poolName, String threadName) {
        SecurityManager securityManager = System.getSecurityManager();
        group = (securityManager != null) ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
        namePrefix = poolName + "-" + poolNumber.getAndIncrement() + "-" + threadName + "-";
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(group, r,
                namePrefix + threadNumber.getAndIncrement(),
                0);
        if (thread.isDaemon()) {
            thread.setDaemon(false);
        }
        if (thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }
        return thread;
    }
}
