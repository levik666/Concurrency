package com.levik.concurrency;

import com.levik.concurrency.serivce.Consumer;
import com.levik.concurrency.serivce.Producer;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class QueueExecutor {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);
        List<String> elements = Arrays.asList("1", "2", "3", "4", "5");

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(new Consumer<String>(queue));
        executorService.submit(new Producer<String>(queue, elements));

        executorService.awaitTermination(100l, TimeUnit.SECONDS);

    }
}
