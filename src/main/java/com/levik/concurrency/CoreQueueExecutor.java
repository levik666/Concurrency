package com.levik.concurrency;

import com.levik.concurrency.serivce.CoreConsumer;
import com.levik.concurrency.serivce.CoreProducer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;

public class CoreQueueExecutor {

    public static void main(String[] args) throws InterruptedException {
        Queue<String> queue = new LinkedList<String>();
        int maxSize = 10;
        String element = "1";

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(new CoreConsumer<String>(maxSize, queue));
        executorService.submit(new CoreProducer<String>(maxSize, queue, element));

        executorService.shutdownNow();

    }
}
