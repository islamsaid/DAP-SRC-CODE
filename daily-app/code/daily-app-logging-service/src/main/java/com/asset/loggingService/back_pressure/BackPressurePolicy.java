package com.asset.loggingService.back_pressure;

import org.springframework.stereotype.Component;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
@Component
public class BackPressurePolicy implements RejectedExecutionHandler  {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) throws RejectedExecutionException {
        try {
            boolean queued = executor.getQueue().offer(r, 100, TimeUnit.MILLISECONDS);
            if(!queued){
                throw new RejectedExecutionException();
            }
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
