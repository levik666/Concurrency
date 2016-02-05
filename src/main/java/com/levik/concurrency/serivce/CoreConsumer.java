package com.levik.concurrency.serivce;

import java.util.Queue;

public class CoreConsumer<E> implements Runnable{

    private Queue<E> queue;
    private int maxSize;

    public CoreConsumer(int maxSize, Queue<E> queue) {
        this.maxSize = maxSize;
        this.queue = queue;
    }

    @Override
    public void run() {

        while (true) {
            synchronized (queue) {
                while (queue.isEmpty()) {
                    System.out.println("Queue is empty. Consumer wait until Producer put something to queue");
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                E element = queue.remove();
                System.out.println("Consumer consume element " + element);
                queue.notifyAll();
            }

        }

    }
}
